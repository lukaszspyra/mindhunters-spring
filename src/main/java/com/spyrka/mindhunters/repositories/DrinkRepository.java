package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.Ingredient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DrinkRepository extends JpaRepository<Drink, Long> {

    //TODO - check if drink exists prior removal (in adminManagementService)


    @Query("SELECT c.name, COUNT(d.drinkName) as quantity FROM Drink d JOIN d.category c  WHERE d.isApproved = true " +
            "GROUP BY c" +
            ".name ORDER BY c.name ASC")
    List getDrinksInAllCategories();


    //liveSearchDrinksByName
    @Query("SELECT d FROM Drink d WHERE LOWER( d.drinkName) LIKE LOWER( ?1 ) and d.isApproved = true")
    List<Drink> findDrinksByDrinkNameContaining(String partialDrinkName, Pageable pageable);


    @Query("SELECT d FROM Drink d JOIN d.drinkIngredients di WHERE di.ingredient IN ( ?1 ) and d.isApproved = true " +
            "GROUP " +
            "BY d ORDER BY COUNT (di.ingredient) DESC ")
    List<Drink> findByIngredients(List<Ingredient> ingredients, Pageable pageable);


    @Query("SELECT d FROM Drink d where d.isApproved = true")
    List<Drink> findAllDrinks(Pageable pageable);


    @Query("SELECT d FROM Drink d WHERE d.category.id IN ( ?1 ) AND d.isApproved = true")
    List<Drink> findByCategories(List<Long> category, Pageable pageable);


    @Query("SELECT d FROM Drink d WHERE d.alcoholStatus IN ( ?1 ) AND d.isApproved = true")
    List<Drink> findByAlcoholStatus(List<String> alcoholStatus, Pageable pageable);


    @Query("SELECT d FROM Drink d  WHERE d.alcoholStatus  IN ( ?2 ) AND d.category.id IN ( ?1 ) AND d" +
            ".isApproved = true")
    List<Drink> findByCategoriesAndAlcoholStatus(List<Long> category, List<String> alcoholStatus, Pageable pageable);


    @Query("SELECT d FROM Drink d where d.isApproved = false ")
    List<Drink> findDrinksToApprove();


    @Query("DELETE FROM DrinkIngredient di where di.drinkId.id = ?1")
    void deleteIngredientsFromDrink(Long drinkId);


    @Query("SELECT d FROM Drink d where d.isApproved = false AND d.manageAction LIKE ?1")
    List<Drink> findOldDrinksToApprove(String action);


    @Query("SELECT d FROM Drink d where d.isApproved = false AND d.manageAction NOT IN (?1)")
    List<Drink> findNewDrinksToApprove(List<String> actions);


    @Query("SELECT COUNT (d) FROM Drink d WHERE d.alcoholStatus IN ( ?1 ) AND d.isApproved = true")
    int countPagesByAlcoholStatus(List<String> alcoholStatus);


    @Query("SELECT COUNT (d) FROM Drink d  WHERE d.alcoholStatus  IN ( ?2 ) AND d.category.id IN " +
            "( ?1 ) AND d.isApproved = true")
    int countPagesByCategoriesAndAlcoholStatus(List<Long> category, List<String> alcoholStatus);


    @Query("SELECT COUNT (d) FROM Drink d WHERE d.category.id IN ( ?1 ) AND d.isApproved = true")
    int countPagesByCategories(List<Long> category);


    @Query("SELECT COUNT (d) FROM Drink d WHERE d.isApproved = true")
    int countPagesFindAll();


    //CountPagesByName - SearchType Service
    @Query("SELECT COUNT(d) FROM Drink d WHERE LOWER (d.drinkName) LIKE LOWER( ?1 ) and d.isApproved = " +
            "true")
    int countPagesByDrinkNameContaining(String partialDrinkName);


/*ALL METHODS HERE USED FOR PAGINATION IN JEE - PROBABLY NOT NEEDED IN SPRING
TODO remove these when complete drinkService methods implementations

private static final Integer LIVE_SEARCH_LIMIT = 10;


     @Override
    public void update(Long id, Drink updatedDrink) {
        entityManager.detach(updatedDrink);
        entityManager.merge(updatedDrink);
    }

    @Override


    }
    */
}
