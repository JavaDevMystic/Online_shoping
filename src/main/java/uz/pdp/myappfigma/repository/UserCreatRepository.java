package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.myappfigma.entity.AuthUser;

import java.util.Optional;

@Repository
public interface UserCreatRepository extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT b FROM AuthUser b where b.username=(:username) and b.password=(:password)")
    Optional<AuthUser> checkByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}