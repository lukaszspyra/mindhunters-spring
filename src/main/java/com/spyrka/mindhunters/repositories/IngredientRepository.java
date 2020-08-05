package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Ingredient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameContaining(@NotNull String name);

    Ingredient findByName(@NotNull String name);

    List<Ingredient> findTop10ByNameContaining(@NotNull String name, Pageable pageable);

}
