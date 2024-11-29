package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.myappfigma.entity.Brand;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b where UPPER(b.name)=UPPER(:name)")
    Optional<Brand> findByName(@Param("name") String name);

    List<Brand> findAll(Specification<Brand> specification);
}
