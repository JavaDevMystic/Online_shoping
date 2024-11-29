package uz.pdp.myappfigma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import uz.pdp.myappfigma.dto.brand.BrandCreateDto;
import uz.pdp.myappfigma.dto.brand.BrandDto;
import uz.pdp.myappfigma.dto.brand.BrandUpdateDto;
import uz.pdp.myappfigma.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/brand/v1/")

public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Long> create(@RequestBody BrandCreateDto dto){
        Long newId = brandService.create(dto);

        if (newId==-1){
            return new BaseResponse<>(new ErrorData("Bro buni oldin yaratgan ekansiz"));
        }
        return new BaseResponse<>(newId);
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Long> update(@RequestParam Long id, @RequestBody BrandUpdateDto dto){
        Long updateId = brandService.update(id, dto);
        if (updateId==-1){
            return new BaseResponse<>(new ErrorData("Bro bunday brand mavjud emas ekanku. Bo'ladigan nara kiriting!!!"),404);
        }
        return new BaseResponse<>(updateId);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<BrandDto> getId(@PathVariable Long id){
        BrandDto dto = brandService.findById(id);
        if (dto==null){
            return new BaseResponse<>(new ErrorData("Bro uzur topolmadik"));
        }
        return new BaseResponse<>(dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Boolean> delet(@PathVariable Long id){
        Boolean delete = brandService.delete(id);
        if (!delete){
            return new BaseResponse<>(new ErrorData("Bro uzur bu brand yo'q ekan. Shuning uchun o'chira olmadik"));
        }

        return new BaseResponse<>(delete);
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<List<BrandDto>> getAll(){
        List<BrandDto> all = brandService.getAll();
        if (all.isEmpty()){
            ErrorData errorData = new ErrorData("Bro hali Brandlar yo'q ekan",404);
            return new BaseResponse<>(errorData);
        }
        return new BaseResponse<>(all);
    }
}