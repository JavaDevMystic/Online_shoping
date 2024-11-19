package uz.pdp.myappfigma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.myappfigma.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByUserId(Long userId);
}