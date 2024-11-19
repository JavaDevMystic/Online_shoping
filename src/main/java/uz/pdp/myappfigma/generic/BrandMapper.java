package uz.pdp.myappfigma.generic;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import uz.pdp.myappfigma.dto.brand.BrandCreateDto;
import uz.pdp.myappfigma.dto.brand.BrandDto;
import uz.pdp.myappfigma.dto.brand.BrandUpdateDto;
import uz.pdp.myappfigma.entity.Brand;

@Mapper(
      unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface BrandMapper extends GenericMapper<Brand, BrandDto, BrandCreateDto>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(BrandUpdateDto dto, @MappingTarget Brand entity);
}
