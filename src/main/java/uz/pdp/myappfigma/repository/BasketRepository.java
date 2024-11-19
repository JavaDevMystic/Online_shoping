package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.myappfigma.entity.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
}
