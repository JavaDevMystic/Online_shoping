package uz.pdp.myappfigma.dto.product;


import org.springframework.data.jpa.domain.Specification;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.entity.Product_;
import uz.pdp.myappfigma.utils.QueryUtil;

public final class ProductSpecs {

    private ProductSpecs() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Product> getSpecification(ProductCriteria criteria) {

        Specification<Product> specs = Specification.where(null);

        if (criteria.getPriceFrom() != null) {
            specs = specs.and(QueryUtil.gte(root -> root.get(Product_.PRICE), criteria.getPriceFrom()));
        }
        if (criteria.getPriceTo() != null) {
            specs = specs.and(QueryUtil.lte(root -> root.get(Product_.PRICE), criteria.getPriceTo()));
        }
        if (criteria.getQuery() != null) {
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
