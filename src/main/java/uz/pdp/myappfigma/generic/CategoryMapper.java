package uz.pdp.myappfigma.generic;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Bean;
import uz.pdp.myappfigma.dto.category.CategoryCreatDto;
import uz.pdp.myappfigma.dto.category.CategoryDto;
import uz.pdp.myappfigma.dto.category.CategoryUpdateDto;
import uz.pdp.myappfigma.entity.Category;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CategoryMapper extends GenericMapper<Category, CategoryDto, CategoryCreatDto>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(CategoryUpdateDto dto, @MappingTarget Category entity);
}
