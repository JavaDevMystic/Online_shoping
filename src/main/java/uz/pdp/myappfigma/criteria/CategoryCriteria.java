package uz.pdp.myappfigma.criteria;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;
import uz.pdp.myappfigma.enums.Gender;
import uz.pdp.myappfigma.generic.GenericCriteria;


@Getter
@Setter
@ParameterObject
public class CategoryCriteria extends GenericCriteria {

    private Gender gender;

    @Override
    public Sort defaultSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }
}