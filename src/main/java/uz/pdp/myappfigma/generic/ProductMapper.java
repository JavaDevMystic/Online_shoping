package uz.pdp.myappfigma.generic;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import uz.pdp.myappfigma.dto.product.ProductCreateDto;
import uz.pdp.myappfigma.dto.product.ProductDto;
import uz.pdp.myappfigma.dto.product.ProductUpdateDto;
import uz.pdp.myappfigma.entity.Product;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper extends GenericMapper<Product, ProductDto, ProductCreateDto> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ProductUpdateDto dto, @MappingTarget Product entity);
}