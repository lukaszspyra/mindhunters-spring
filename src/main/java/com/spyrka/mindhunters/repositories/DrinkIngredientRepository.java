package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.DrinkIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DrinkIngredientRepository extends JpaRepository<DrinkIngredient, Long> {

    @Query("DELETE FROM DrinkIngredient di where di.drinkId.id = ?1")
    @Modifying
    void deleteIngredientsFromDrink(Long drinkId);

}
