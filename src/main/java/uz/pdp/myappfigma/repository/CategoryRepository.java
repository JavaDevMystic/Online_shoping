package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.myappfigma.entity.Category;
import uz.pdp.myappfigma.entity.Product;
import uz.pdp.myappfigma.enums.Gender;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> , JpaSpecificationExecutor<Category> {
    @Query("SELECT c FROM Category c WHERE c.childId = 0 AND c.gender = :gender")
    List<Category> findByGender(@Param("gender") Gender gender);
}
