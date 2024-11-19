package uz.pdp.myappfigma.service;

import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.brand.BrandCreateDto;
import uz.pdp.myappfigma.generic.BrandMapper;
import uz.pdp.myappfigma.repository.BrandRepository;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }


    public Boolean create(BrandCreateDto dto){
        return true;
    }
}