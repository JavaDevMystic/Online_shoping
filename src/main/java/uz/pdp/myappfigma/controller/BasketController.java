package uz.pdp.myappfigma.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.dto.basket.BasketCreateDto;
import uz.pdp.myappfigma.dto.basket.BasketDto;
import uz.pdp.myappfigma.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/basket/v1/")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Long> create(@Valid @RequestBody BasketCreateDto dto) {

        Long newId = basketService.create(dto);
        return new BaseResponse<>(newId);
    }

    @GetMapping("/view")
    public BaseResponse<List<BasketDto>> getAll() {

        List<BasketDto> basketDtos = basketService.getAll();

        return new BaseResponse<>(basketDtos);

    }

    @PostMapping("/cancel/order/{id}")
    public BaseResponse<Boolean> cancel(@PathVariable Long id) {
        boolean canseled = basketService.canselOrder(id);
        return new BaseResponse<>(canseled);
    }

}
