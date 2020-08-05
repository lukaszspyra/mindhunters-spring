package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByDrinkId(Long drink_id);
}
