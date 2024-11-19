package uz.pdp.myappfigma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.pdp.myappfigma.dto.product.ProductCriteria;
import uz.pdp.myappfigma.dto.product.ProductSpecs;
import uz.pdp.myappfigma.entity.Product;


public interface ProductRepository
        extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    default Page<Product> findPage(ProductCriteria criteria) {
        Specification<Product> specification = ProductSpecs.getSpecification(criteria);
        return findAll(specification, PageRequest.of(criteria.getPage(), criteria.getSize()));
    }

}