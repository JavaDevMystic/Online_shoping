package uz.pdp.myappfigma.service;

import org.springframework.stereotype.Service;
import uz.pdp.myappfigma.dto.auth.SessionUser;
import uz.pdp.myappfigma.dto.brand.BrandCreateDto;
import uz.pdp.myappfigma.dto.brand.BrandDto;
import uz.pdp.myappfigma.dto.brand.BrandUpdateDto;
import uz.pdp.myappfigma.entity.Brand;
import uz.pdp.myappfigma.generic.BrandMapper;
import uz.pdp.myappfigma.repository.BrandRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final SessionUser sessionUser;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper, SessionUser sessionUser) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
        this.sessionUser = sessionUser;
    }


    public Long create(BrandCreateDto dto) {

        String brandName = dto.name();
        Optional<Brand> byName = brandRepository.findByName(brandName);
        if (byName.isPresent()){
            return -1L;
        }

        Brand entity = brandMapper.toEntity(dto);
        entity.setCreatedBy(sessionUser.getId());
        brandRepository.save(entity);
        return entity.getId();
    }

    public Long update(Long id, BrandUpdateDto dto) {
        Long userId = sessionUser.getId();
        Brand brand = brandRepository.findById(id)
                .orElse(null);
        if (brand==null){
            return -1L;
        }
        brand.setUpdatedBy(userId);
        brandMapper.partialUpdate(dto, brand);
        brandRepository.save(brand);
        return brand.getId();
    }

    public Boolean delete(Long id){
        Brand brand = brandRepository.findById(id)
                .orElse(null);
        if (brand==null){
            return false;
        }
        brandRepository.delete(brand);
        return true;
    }

    public BrandDto findById(Long id){

        return brandRepository.findById(id)
                .map(brandMapper::toDto)
                .orElse(null);
    }

    public List<BrandDto> getAll() {
        List<BrandDto> collect = brandRepository.findAll().stream().map(brand -> new BrandDto(
                        brand.getId(),
                        brand.getName()
                ))
                .collect(Collectors.toList());

        return collect.isEmpty()? Collections.emptyList():collect;

    }
}