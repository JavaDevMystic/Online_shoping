package uz.pdp.myappfigma.service;

import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.basket.BasketCreateDto;
import uz.pdp.myappfigma.dto.category.CategoryCreatDto;
import uz.pdp.myappfigma.entity.Basket;
import uz.pdp.myappfigma.generic.BasketMapper;
import uz.pdp.myappfigma.repository.BasketRepository;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;

    public BasketService(BasketRepository basketRepository, BasketMapper basketMapper) {
        this.basketRepository = basketRepository;
        this.basketMapper = basketMapper;
    }

//    public Long create(BasketCreateDto dto){
//        Long categoryId = dto.getCategoryId();
//        Basket entity = basketMapper.toEntity(dto);
//
//    }
}
