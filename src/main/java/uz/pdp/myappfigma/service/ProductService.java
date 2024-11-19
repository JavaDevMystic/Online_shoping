package uz.pdp.myappfigma.service;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.product.ProductCreateDto;
import uz.pdp.myappfigma.generic.ProductCriteria;
import uz.pdp.myappfigma.dto.product.ProductDao;
import uz.pdp.myappfigma.dto.product.ProductDto;
import uz.pdp.myappfigma.generic.ProductMapper;
import uz.pdp.myappfigma.dto.product.ProductUpdateDto;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.dto.pageDto.PageDto;
import uz.pdp.myappfigma.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, EntityManager entityManager, ProductDao productDao, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Transactional
    public PageDto<ProductDto> getPage(ProductCriteria criteria) {
        Page<ProductDto> page = productRepository.findPage(criteria).map(productMapper::toDto);
        return new PageDto<>(page);
    }

    public ProductDto get(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found:" + id));
        System.out.println(productMapper.toDto(product));
        return productMapper.toDto(product);
    }

    public Long create(ProductCreateDto dto) {
        Long id = dto.categoryId();
        if (!categoryService.findByIdNotNull(id)) {
            return null;
        }
        Product product = productMapper.toEntity(dto);
        productRepository.save(product);
        return product.getId();
    }

    public Long update(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found:" + id));
        productMapper.partialUpdate(dto, product);
        productRepository.save(product);
        return product.getId();
    }

    public Boolean delete(Long id){

        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public boolean findByIdNotNull(Long id){
        return productRepository.findById(id).isPresent();
    }

    @Transactional
    public List<ProductDto> findAllWithDiscount() {
        List<Product> products = productRepository.findAll().stream()
                .filter(product -> product.getDiscount() > 0)
                .collect(Collectors.toList());
        return productMapper.toDto(products);
    }
}
