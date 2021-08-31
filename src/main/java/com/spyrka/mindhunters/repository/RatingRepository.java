package com.spyrka.mindhunters.repository;

import com.spyrka.mindhunters.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByDrinkId(Long drink_id);
}
