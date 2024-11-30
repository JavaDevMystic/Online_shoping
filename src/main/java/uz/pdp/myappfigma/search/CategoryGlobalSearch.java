package uz.pdp.myappfigma.search;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.pdp.myappfigma.criteria.CategoryCriteria;
import uz.pdp.myappfigma.entity.Category;
import uz.pdp.myappfigma.entity.Category_;
import uz.pdp.myappfigma.utils.QueryUtil;

@Component
public class CategoryGlobalSearch implements GlobalS<Category, CategoryCriteria> {

    @Override
    public Specification<Category> getSpecification(CategoryCriteria criteria) {
        Specification<Category> specs = Specification.where(null);

        if (criteria.getQuery() != null && !criteria.getQuery().isEmpty()) {
            specs = specs.and(QueryUtil.search(criteria.getQuery(), Category_.name));
        }

        if (criteria.getQuery() != null) {
            specs = specs.and(QueryUtil.search(criteria.getQuery(), Category_.name));
        }

        return specs;
    }
}
