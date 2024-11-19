package uz.pdp.myappfigma.search;

import org.springframework.data.jpa.domain.Specification;

public interface GlobalS<T, C> {
    Specification<T> getSpecification(C criteria);
}
