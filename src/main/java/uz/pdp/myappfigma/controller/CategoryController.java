package uz.pdp.myappfigma.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.dto.ErrorData;
import uz.pdp.myappfigma.dto.category.CategoryCreatDto;
import uz.pdp.myappfigma.dto.category.CategoryDto;
import uz.pdp.myappfigma.dto.category.CategoryUpdateDto;
import uz.pdp.myappfigma.enums.Gender;
import uz.pdp.myappfigma.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category/v1")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Long> create(@Valid @RequestBody CategoryCreatDto dto) {
        Long newId = categoryService.create(dto);
        if (newId==-1){
            return new BaseResponse<>(new ErrorData("Bro kechirasiz bunday Categorya bor ekan "));
        }
        return new BaseResponse<>(newId);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<CategoryDto> getId(@Valid @PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findById(id);
        if (categoryDto==null){
            return new BaseResponse<>(new ErrorData("Kechirasiz bro topolmadik"));
        }
        return new BaseResponse<>(categoryDto);
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Long> update(@Valid @PathVariable Long id, @Valid @RequestBody CategoryUpdateDto dto) {
        Long updateId = categoryService.update(id, dto);
        if (updateId==-1){
            return new BaseResponse<>(new ErrorData("Kechirasiz bro bunday categorya yo'q ekan!!"));
        }
        return new BaseResponse<>(updateId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<List<CategoryDto>> getAll(){
        List<CategoryDto> all = categoryService.getAll();
        if (all.isEmpty()){
            return new BaseResponse<>(new ErrorData("Bro ctegory yo'q ekanku"));
        }
        return new BaseResponse<>(all);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Boolean> delete(@Valid @PathVariable Long id){
        Boolean delete = categoryService.delete(id);
        if (!delete){
            return new BaseResponse<>(new ErrorData("Bro shu qandaydir neto catolik bo'ldima deymanda. Bir qaytadan bo'ladigan idni kiritib ko'ring"));
        }
        return new BaseResponse<>(delete);
    }

    @GetMapping("/childCategory/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<List<CategoryDto>> parentCategory(@PathVariable Long id){
        List<CategoryDto> childCategory = categoryService.getChildCategory(id);
        if (childCategory.isEmpty()){
            return new BaseResponse<>(new ErrorData("Bro kechirasiz buning child cantegoriyasi yoq' eka"));
        }
        return new BaseResponse<>(childCategory);
    }

}
