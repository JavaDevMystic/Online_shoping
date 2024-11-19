package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.myappfigma.entity.AuthUser;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findByPassword(String password);

    Optional<AuthUser> findByUsernameAndPassword(String username,String password);
}