package uz.pdp.myappfigma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.dto.brand.BrandCreateDto;
import uz.pdp.myappfigma.dto.brand.BrandDto;
import uz.pdp.myappfigma.dto.brand.BrandUpdateDto;
import uz.pdp.myappfigma.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // Get all brands
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<BrandDto> getAllBrands() {
        return brandService.getAllBrands();
    }

    // Get brand by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BaseResponse<BrandDto> getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id)
                .map(brandDto -> new BaseResponse(brandDto))
                .orElseGet(() -> new BaseResponse<>(null));
    }

    // Create a new brand
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<BrandDto> create(@RequestBody BrandCreateDto brandCreateDto) {
        BrandDto createdBrand = brandService.createBrand(brandCreateDto);
        return new BaseResponse<>(createdBrand);
    }

    // Update an existing brand
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<BrandDto> update(@PathVariable Long id, @RequestBody BrandUpdateDto brandUpdateDto) {
        BrandDto updatedBrand = brandService.updateBrand(id, brandUpdateDto);
        if (updatedBrand != null) {
            return new BaseResponse<>(updatedBrand);
        } else {
            return new BaseResponse<>(null);
        }
    }

    // Delete a brand
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Boolean> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return new BaseResponse<>(null);
    }
}
