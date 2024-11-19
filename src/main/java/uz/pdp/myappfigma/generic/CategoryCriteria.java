package uz.pdp.myappfigma.generic;


import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;


@Getter
@Setter
@ParameterObject
public class CategoryCriteria extends GenericCriteria {

    private Long priceFrom;
    private Long priceTo;

    @Override
    public Sort defaultSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }
}
