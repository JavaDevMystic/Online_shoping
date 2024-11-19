package uz.pdp.myappfigma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.brand.BrandCreateDto;
import uz.pdp.myappfigma.dto.brand.BrandDto;
import uz.pdp.myappfigma.dto.brand.BrandUpdateDto;
import uz.pdp.myappfigma.entity.Brand;
import uz.pdp.myappfigma.repository.BrandRepositary;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepositary brandRepositary;

    @Autowired
    public BrandService(BrandRepositary brandRepositary) {
        this.brandRepositary = brandRepositary;
    }

    private BrandDto toBrandDto(Brand brand) {
        return new BrandDto(brand.getId(), brand.getName());
    }

    private Brand toBrandEntity(BrandCreateDto brandCreateDto) {
        return new Brand(null, brandCreateDto.name());
    }

    private Brand toBrandEntity(Long id, BrandUpdateDto brandUpdateDTO) {
        return new Brand();
    }

    public List<BrandDto> getAllBrands() {
        return brandRepositary.findAll()
                .stream()
                .map(this::toBrandDto)
                .collect(Collectors.toList());
    }

    public Optional<BrandDto> getBrandById(Long id) {
        return brandRepositary.findById(id)
                .map(this::toBrandDto);
    }

    public BrandDto createBrand(BrandCreateDto brandCreateDto) {
        Brand brand = toBrandEntity(brandCreateDto);
        Brand savedBrand = brandRepositary.save(brand);
        return toBrandDto(savedBrand);
    }


    public BrandDto updateBrand(Long id, BrandUpdateDto brandUpdateDto) {
        if (brandRepositary.existsById(id)) {
            Brand brand = toBrandEntity(id, brandUpdateDto);
            Brand updatedBrand = brandRepositary.save(brand);
            return toBrandDto(updatedBrand);
        }
        return null;
    }


    public void deleteBrand(Long id) {
        brandRepositary.deleteById(id);
    }
}
