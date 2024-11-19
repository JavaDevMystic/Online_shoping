package uz.pdp.myappfigma.generic;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import uz.pdp.myappfigma.dto.basket.BasketCreateDto;
import uz.pdp.myappfigma.dto.basket.BasketDto;
import uz.pdp.myappfigma.dto.basket.BasketUpdateDto;

import uz.pdp.myappfigma.entity.Basket;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface BasketMapper extends GenericMapper<Basket, BasketDto, BasketCreateDto> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(BasketUpdateDto dto, @MappingTarget Basket entity);
}