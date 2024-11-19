package uz.pdp.myappfigma.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.configuration.security.UserSession;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.basket.BasketCreateDto;
import uz.pdp.myappfigma.dto.basket.BasketDto;
import uz.pdp.myappfigma.dto.category.CategoryCreatDto;
import uz.pdp.myappfigma.dto.product.ProductDto;
import uz.pdp.myappfigma.entity.AuthUser;
import uz.pdp.myappfigma.entity.Basket;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.generic.BasketMapper;
import uz.pdp.myappfigma.repository.AuthUserRepository;
import uz.pdp.myappfigma.repository.BasketRepository;
import uz.pdp.myappfigma.repository.ProductRepository;

import java.time.LocalDateTime;
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
        ///productni shu payti miqdorini bitaga kamaytirib qo'yish kerak
        Basket entity = basketMapper.toEntity(dto);
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
        basketRepository.save(basket);
        return true;
    }


    public List<BasketDto> getAll() {
        Long userId = sessionUser.getId();

        return basketRepository.findAll()
                .stream()
                .filter(basket -> basket.getAuthUser().getId().equals(userId))
                .map(basket -> new BasketDto(
                        basket.getId(),
                        basket.getAuthUser().getId(),
                        new ProductDto(
                                basket.getProduct().getName(),
                                basket.getProduct().getPrice()
                        ),

                        basket.getIsCanselOrder()
                ))
                .collect(Collectors.toList());
    }


}
