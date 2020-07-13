package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
