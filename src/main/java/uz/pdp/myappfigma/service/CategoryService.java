package uz.pdp.myappfigma.service;

import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.category.CategoryCreatDto;
import uz.pdp.myappfigma.dto.category.CategoryDto;
import uz.pdp.myappfigma.dto.category.CategoryUpdateDto;
import uz.pdp.myappfigma.entity.Category;
import uz.pdp.myappfigma.enums.Gender;
import uz.pdp.myappfigma.generic.CategoryMapper;
import uz.pdp.myappfigma.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SessionUser sessionUser;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, SessionUser sessionUser) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.sessionUser = sessionUser;
    }

    public Long create(CategoryCreatDto dto) {
        String name = dto.name();

        Optional<Category> byName = categoryRepository.findByName(name);
        if (byName.isPresent()) {
            return -1L;
        }

        Category category = categoryMapper.toEntity(dto);
        category.setCreatedBy(sessionUser.getId());
        categoryRepository.save(category);
        return category.getId();
    }

    public Long update(Long id, CategoryUpdateDto dto) {
        Long id1 = sessionUser.getId();

        Category category = categoryRepository.findById(id)
                .orElse(null);
        if (category == null) {
            return -1L;
        }
        category.setUpdatedBy(id1);
        categoryMapper.partialUpdate(dto, category);
        categoryRepository.save(category);
        return category.getId();


    }

    public Boolean delete(Long id) {

        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean findByIdNotNull(Long id) {
        return categoryRepository.findById(id).isPresent();
    }

    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElse(null);
    }

    public List<CategoryDto> getAll() {

        List<CategoryDto> collect = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
//        List<CategoryDto> collect = categoryRepository.findAll()
//                .stream()
//                .map(category -> new CategoryDto(
//                        category.getId(),
//                        category.getName(),
//                        category.getGender(),
//                        category.getChildId(),
//                        category.getBrandId(),
//                        category.getCreatedAt(),
//                        category.getUpdatedAt(),
//                        category.getCreatedBy(),
//                        category.getUpdatedBy()
//
//                ))
//                .toList();
        return collect.isEmpty() ? Collections.emptyList() : collect;
    }


    public List<CategoryDto> getCategoriesByGender(Gender gender) {
        List<Category> categories = categoryRepository.findByGender(gender);
        return categories.stream().map(categoryMapper::toDto).toList();
    }

    public List<CategoryDto> getChildCategory(Long id) {
        List<Category> category = categoryRepository.findByCategory(id);

        List<CategoryDto> list = category
                .stream()
                .map(category1 -> categoryMapper.toDto(category1))
                .toList();
        return list.isEmpty()?Collections.emptyList():list;
    }
}
