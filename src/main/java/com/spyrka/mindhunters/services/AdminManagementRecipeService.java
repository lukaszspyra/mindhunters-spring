package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.*;
import com.spyrka.mindhunters.repositories.DrinkRepository;
import com.spyrka.mindhunters.repositories.RatingRepository;
import com.spyrka.mindhunters.repositories.StatisticsRepository;
import com.spyrka.mindhunters.services.mappers.FullDrinkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdminManagementRecipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminManagementRecipeService.class.getName());

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private MeasureService measureService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    CategoryService categoryService;

    @Transactional
    public boolean proposeDeleteDrink(Long id, String email){
        Drink existingDrink = drinkRepository.findById(id).get();
        Drink drinkToBeDeleted = new Drink();

        if (existingDrink == null){
            return false;
        }
        drinkToBeDeleted.setDrinkName(existingDrink.getDrinkName());
        drinkToBeDeleted.setCategory(existingDrink.getCategory());
        drinkToBeDeleted.setAlcoholStatus(existingDrink.getAlcoholStatus());
        drinkToBeDeleted.setRecipe(existingDrink.getRecipe());

        List<DrinkIngredient> drinkIngredientsList = getDrinkIngredients(existingDrink, drinkToBeDeleted);

        existingDrink.setDrinkIngredients(drinkIngredientsList);

        drinkToBeDeleted.setParentId(id);
        drinkToBeDeleted.setManageAction("DELETE");
        drinkToBeDeleted.setImage(existingDrink.getImage());

        LocalDateTime formatDateTime = LocalDateTime.now();
        drinkToBeDeleted.setDate(formatDateTime);
        drinkToBeDeleted.setApproved(false);
        drinkToBeDeleted.setConfirmUserEmail(email);

        drinkRepository.save(drinkToBeDeleted);
        return true;
    }

    private List<DrinkIngredient> getDrinkIngredients(Drink existingDrink, Drink drinkToBeDeleted) {
        List<String> measures = existingDrink.getDrinkIngredients().stream()
                .filter(Objects::nonNull)
                .map(drinkIngredient -> drinkIngredient.getMeasure())
                .map(measure -> measure.getQuantity())
                .collect(Collectors.toList());

        List<String> ingredients = existingDrink.getDrinkIngredients().stream()
                .filter(Objects::nonNull)
                .map(drinkIngredient -> drinkIngredient.getIngredient())
                .map(ingredient -> ingredient.getName())
                .collect(Collectors.toList());

        List<Measure> measureList = new ArrayList<>();
        List<Ingredient> ingredientList = new ArrayList<>();

        for (String measure : measures) {
            measureList.add(measureService.getOrCreate(measure));
        }
        for (String ingredient : ingredients) {
            ingredientList.add(ingredientService.getOrCreate(ingredient.toString()));
        }

        List<DrinkIngredient> drinkIngredientsList = new ArrayList<>();

        for (int i = 0; i < measureList.size(); i++) {
            DrinkIngredient drinkIngredient = new DrinkIngredient();

            drinkIngredient.setMeasure(measureList.get(i));
            drinkIngredient.setIngredient(ingredientList.get(i));

            drinkIngredient.setDrinkId(drinkToBeDeleted);

            drinkIngredientsList.add(drinkIngredient);
        }
        return drinkIngredientsList;
    }


    @Transactional
    public boolean deleteDrinkById(Long id) {
        if (drinkRepository.findById(id).isPresent()){
            Drink drink = drinkRepository.findById(id).get();

            List<User> users = drink.getUsers();
            for (User user : users) {
                user.getDrinks().remove(drink);
            }
            statisticsRepository.deleteStatisticsByDrink(drink);
            drinkRepository.delete(drink);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addOrUpdateDrink(Long id, Drink newDrink, ContextHolder contextHolder) {
        Drink editedDrink = drinkRepository.findById(id).orElse(new Drink());

        if (newDrink != null) {
            editedDrink.setConfirmUserEmail(newDrink.getConfirmUserEmail());
            editedDrink.setDrinkName(newDrink.getDrinkName());
            editedDrink.setAlcoholStatus(newDrink.getAlcoholStatus());
            editedDrink.setImage(newDrink.getImage());
            editedDrink.setRecipe(newDrink.getRecipe());
            LocalDateTime formatDateTime = LocalDateTime.now();
            editedDrink.setDate(formatDateTime);


            Category category =categoryService.getOrCreate(newDrink.getCategory().getName()) ;
            editedDrink.setCategory(category);


            List<DrinkIngredient> drinkIngredientsList = getDrinkIngredients(newDrink, editedDrink);
            editedDrink.getDrinkIngredients().clear();
            editedDrink.setDrinkIngredients(drinkIngredientsList);


        }

        if (id != 0L){
            editedDrink.setManageAction("EDIT");
            drinkRepository.deleteIngredientsFromDrink(id);
            drinkRepository.save(editedDrink);
        } else {
            editedDrink.setManageAction("ADD");
            drinkRepository.save(editedDrink);

        }
        return true;
    }

    public Drink setApproved(long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow();
        drink.setApproved(true);
        drinkRepository.save(drink);

        return drink;
    }


    public Drink rejectDrinkProposal(long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow();
        drinkRepository.delete(drink);
        return drink;
    }

    public Drink setApprovedEditedDrink(long drinkId) {
        Drink newDrink = drinkRepository.findById(drinkId).orElseThrow();
        statisticsRepository.deleteStatisticsByDrink(newDrink);

        Long newDrinkParentId = newDrink.getParentId();
        Drink oldDrink = drinkRepository.findById(newDrinkParentId).orElseThrow();
        statisticsRepository.deleteStatisticsByDrink(oldDrink);

        drinkRepository.delete(newDrink);

        newDrink.setApproved(true);
        newDrink.setId(newDrinkParentId);

        drinkRepository.save(newDrink);

        return newDrink;
    }

    public Drink setApprovedDeleteDrink(long drinkId) {
        Drink drink= drinkRepository.findById(drinkId).orElseThrow();
        drinkRepository.delete(drink);

        Long idTobeDeleted = drink.getParentId();
        Drink drinkTobeDeleted = drinkRepository.findById(idTobeDeleted).orElseThrow();

        statisticsRepository.deleteStatisticsByDrink(drinkTobeDeleted);

        ratingRepository.findByDrinkId(idTobeDeleted).ifPresent(rating1 -> ratingRepository.delete(rating1));

        drinkRepository.delete(drinkTobeDeleted);

        return drink;
    }
}
