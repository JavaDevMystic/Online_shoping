package uz.pdp.myappfigma.generic;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import uz.pdp.myappfigma.dto.auth.AuthUserCreateDto;
import uz.pdp.myappfigma.dto.auth.AuthUserDto;
import uz.pdp.myappfigma.dto.auth.AuthUserUpdateDto;
import uz.pdp.myappfigma.entity.AuthUser;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AuthUserMapper extends GenericMapper<AuthUser, AuthUserDto, AuthUserCreateDto>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(AuthUserUpdateDto dto, @MappingTarget AuthUser entity);
}
