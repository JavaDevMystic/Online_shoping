package uz.pdp.myappfigma.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.configuration.security.UserSession;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.basket.BasketCreateDto;
import uz.pdp.myappfigma.dto.basket.BasketCurrentDto;
import uz.pdp.myappfigma.dto.basket.BasketDto;
import uz.pdp.myappfigma.dto.product.ProductDto;
import uz.pdp.myappfigma.entity.AuthUser;
import uz.pdp.myappfigma.entity.Basket;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.generic.BasketMapper;
import uz.pdp.myappfigma.repository.AuthUserRepository;
import uz.pdp.myappfigma.repository.BasketRepository;
import uz.pdp.myappfigma.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;
    private final AuthUserRepository authUserRepository;
    private final ProductRepository productRepository;
    private final SessionUser sessionUser;

    public BasketService(BasketRepository basketRepository, BasketMapper basketMapper, AuthUserRepository authUserRepository, ProductRepository productRepository, UserSession userSession, SessionUser sessionUser) {
        this.basketRepository = basketRepository;
        this.basketMapper = basketMapper;
        this.authUserRepository = authUserRepository;
        this.productRepository = productRepository;
        this.sessionUser = sessionUser;
    }

    public Long create(BasketCreateDto dto) {
        Long productId = dto.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Bro mavjud bo'lmagan productni kiritmoqchi bo'lyabsiz"));

        if (product.getCount() < dto.getQuantity()) {
            throw new IllegalArgumentException("Productni miqdori yetarli emas?");
        }
        product.setCount(product.getCount() - dto.getQuantity());
        ///productni shu payti miqdorini bitaga kamaytirib qo'yish kerak

        Basket entity = basketMapper.toEntity(dto);
        entity.setTotalAmount((long) (dto.getQuantity() * product.getPrice()));
        Long id = sessionUser.getId();
        AuthUser authUser = authUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User topilmadi bro" + id));
        entity.setAuthUser(authUser);
        entity.setProduct(product);
        entity.setIsCanselOrder(true);
        basketRepository.save(entity);
        return entity.getId();

    }


    public boolean canselOrder(Long id) {

        Basket basket = basketRepository.findById(id).orElseThrow(() -> new RuntimeException("Bro bunday product categoryangizda mavjud emas"));

        basket.setIsCanselOrder(false);
        // shu payti qaytadan productni miqdorini ++ qilib oshirib qo'shish kerak

        Product product = basket.getProduct();

        product.setCount(product.getCount() + basket.getQuantity());
        productRepository.save(product);

        basketRepository.save(basket);
        return true;
    }


    public List<BasketDto> getAll() {
        Long userId = sessionUser.getId();

        return basketRepository.findAll()
                .stream()
                .filter(basket ->
                        basket.getAuthUser().getId().equals(userId) &&
                                basket.getIsCanselOrder() &&
                                !basket.getAddHistory())
                .map(basket -> new BasketDto(
                        basket.getId(),
                        basket.getAuthUser().getId(),
                        new ProductDto(
                                basket.getProduct().getName(),
                                basket.getProduct().getPrice(),
                                basket.getProduct().getCount()
                        ),
                        basket.getIsCanselOrder(),
                        basket.getQuantity(),
                        basket.getTotalAmount()
                ))
                .collect(Collectors.toList());
    }

    public BasketCurrentDto totalMoneyForUser(Long id) {

        List<Basket> baskets = new ArrayList<>();
        for (Basket basket : basketRepository.findAll()) {
            if (basket.getAuthUser().getId().equals(id) &&
                    basket.getIsCanselOrder() &&
                    !basket.getAddHistory()) {
                baskets.add(basket);
            }
        }

        List<Product> products = baskets.stream()
                .map(Basket::getProduct)
                .collect(Collectors.toList());

        Long totalAmount = baskets.stream()
                .mapToLong(Basket::getTotalAmount)
                .sum();

        return new BasketCurrentDto(products, totalAmount);
    }


    public void updateAddHistory(Long userId) {
        List<Basket> baskets = basketRepository.findAll()
                .stream()
                .filter(basket -> basket.getAuthUser().getId().equals(userId) && !basket.getAddHistory())
                .toList();

        for (Basket basket : baskets) {
            basket.setAddHistory(true);
            basketRepository.save(basket);
        }
    }


}
