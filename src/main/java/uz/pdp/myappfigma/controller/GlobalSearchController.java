package uz.pdp.myappfigma.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.criteria.CategoryCriteria;
import uz.pdp.myappfigma.criteria.ProductCriteria;
import uz.pdp.myappfigma.entity.Category;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.repository.CategoryRepository;
import uz.pdp.myappfigma.repository.ProductRepository;
import uz.pdp.myappfigma.search.CategoryGlobalSearch;
import uz.pdp.myappfigma.search.ProductGlobalSearch;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GlobalSearchController {

    private final ProductRepository productRepository;
    private final ProductGlobalSearch productGlobalSearch;
    private final CategoryRepository categoryRepository;
    private final CategoryGlobalSearch categoryGlobalSearch;

    public GlobalSearchController(ProductRepository productRepository, ProductGlobalSearch productGlobalSearch, CategoryRepository categoryRepository, CategoryGlobalSearch categoryGlobalSearch) {
        this.productRepository = productRepository;
        this.productGlobalSearch = productGlobalSearch;
        this.categoryRepository = categoryRepository;
        this.categoryGlobalSearch = categoryGlobalSearch;
    }

    @GetMapping("/search/product")
    public List<Product> search(@RequestParam String query) {
        ProductCriteria criteria = new ProductCriteria();
        criteria.setQuery(query);

        return productRepository.findAll(productGlobalSearch.getSpecification(criteria));
    }

    @GetMapping("/search/category")
    public List<Category> searchCategory(@RequestParam String query) {
        CategoryCriteria criteria = new CategoryCriteria();
        criteria.setQuery(query);

        return categoryRepository.findAll(categoryGlobalSearch.getSpecification(criteria));
    }
}
