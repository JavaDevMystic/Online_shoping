package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.myappfigma.entity.Brand;

@Repository
public interface BrandRepositary extends JpaRepository<Brand, Long> {


}
