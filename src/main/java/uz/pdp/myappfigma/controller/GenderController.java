package uz.pdp.myappfigma.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.dto.category.CategoryDto;
import uz.pdp.myappfigma.enums.Gender;
import uz.pdp.myappfigma.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/gender/v1")
public class GenderController {


    private final CategoryService categoryService;

    public GenderController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/parentCategory")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<List<CategoryDto>> getCategoriesByGender(@RequestParam Gender gender) {
        if (gender != null) {
            List<CategoryDto> categories = categoryService.getCategoriesByGender(gender);
            return new BaseResponse<>(categories);
        } else {
            List<CategoryDto> categoriesAll = categoryService.getCategoriesAll();
            return new BaseResponse<>(categoriesAll);
        }
    }
}
