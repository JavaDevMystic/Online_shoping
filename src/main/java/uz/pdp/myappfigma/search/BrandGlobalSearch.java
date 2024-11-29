package uz.pdp.myappfigma.search;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.pdp.myappfigma.criteria.BrandCriteria;
import uz.pdp.myappfigma.entity.Brand;
import uz.pdp.myappfigma.entity.Brand_;
import uz.pdp.myappfigma.utils.QueryUtil;

@Component
public class BrandGlobalSearch implements GlobalS<Brand, BrandCriteria> {

    @Override
    public Specification<Brand> getSpecification(BrandCriteria criteria) {
        Specification<Brand> specs = Specification.where(null);

        if (criteria.getQuery() != null && !criteria.getQuery().isEmpty()) {
            specs = specs.and(QueryUtil.search(criteria.getQuery(), Brand_.name));
        }

        return specs;
    }
}
