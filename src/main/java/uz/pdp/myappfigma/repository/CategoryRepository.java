package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.myappfigma.entity.Category;
import uz.pdp.myappfigma.enums.Gender;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> , JpaSpecificationExecutor<Category> {
    @Query("SELECT c FROM Category c WHERE c.childId = 0 AND c.gender = :gender")
    List<Category> findByGender(@Param("gender") Gender gender);

    @Query("SELECT c FROM Category c WHERE c.childId = 0")
    List<Category> findAll();


    @Query("SELECT b FROM Category b where UPPER(b.name)=UPPER(:name)")
    Optional<Category> findByName(@Param("name") String name);

    @Query("SELECT b FROM Category b where b.childId=(:id)")
    List<Category> findByCategory(@Param("id") Long id);
}
