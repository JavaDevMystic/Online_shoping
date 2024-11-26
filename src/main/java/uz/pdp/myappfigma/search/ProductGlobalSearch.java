package uz.pdp.myappfigma.search;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.pdp.myappfigma.criteria.ProductCriteria;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.entity.Product_;
import uz.pdp.myappfigma.utils.QueryUtil;

@Component
public class ProductGlobalSearch implements GlobalS<Product, ProductCriteria> {

    @Override
    public Specification<Product> getSpecification(ProductCriteria criteria) {
        Specification<Product> specs = Specification.where(null);

        if (criteria.getPriceFrom() != null) {
            specs = specs.and(QueryUtil.gte(root -> root.get(Product_.PRICE), criteria.getPriceFrom()));
        }
        if (criteria.getPriceTo() != null) {
            specs = specs.and(QueryUtil.lte(root -> root.get(Product_.PRICE), criteria.getPriceTo()));
        }

        if (criteria.getQuery() != null && !criteria.getQuery().isEmpty()) {
            specs = specs.and(QueryUtil.search(criteria.getQuery(), Product_.name));
        }

        return specs;
    }

    public static Specification<Product> priceFromSpecification(Long priceFrom) {
        return (root, qb, cb) -> cb.greaterThanOrEqualTo(root.get(Product_.PRICE), priceFrom);
    }

    public static Specification<Product> priceToSpecification(Long priceTo) {
        return (root, qb, cb) -> cb.lessThanOrEqualTo(root.get(Product_.PRICE), priceTo);
    }
}
