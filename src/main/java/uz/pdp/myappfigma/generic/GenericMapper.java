package uz.pdp.myappfigma.generic;



import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


public interface GenericMapper<E, D, CD> {
    E toEntity(CD dto);

    D toDto(E entity);

    List<D> toDto(List<E> entity);


}
