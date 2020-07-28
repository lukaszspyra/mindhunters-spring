package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.*;
import com.spyrka.mindhunters.repositories.DrinkRepository;
import com.spyrka.mindhunters.repositories.RatingRepository;
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
    private StatisticsRepositoryBean statisticsRepositoryBean;

    @Autowired
    private MeasureService measureService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FullDrinkMapper fullDrinkMapper;

    @Autowired
    CategoryService categoryService;

    @Transactional
    public boolean proposeDeleteDrink(Long id, String email){
        Drink existingDrink = drinkRepository.findDrinkById(id);
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
        Drink drink = drinkRepository.findDrinkById(id);

        List<User> users = drink.getUsers();
        for (User user : users) {
            user.getDrinks().remove(drink);
        }
        statisticsRepositoryBean.deleteStatisticsByDrink(drink);
        drinkRepository.delete(id);
        return true;
    }

    @Transactional
    public boolean addOrUpdateDrink(Long id, Drink newDrink, ContextHolder contextHolder) {
        Drink editedDrink = drinkRepository.findDrinkById(id);


        if (editedDrink == null){
            editedDrink = new Drink();
        }

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
            drinkRepository.update(id, editedDrink);
        } else {
            editedDrink.setManageAction("ADD");
            drinkRepository.save(editedDrink);

        }
        return true;
    }

    public Drink setApproved(long drinkId) {
        Drink drink = drinkRepository.findDrinkById(drinkId);
        drink.setApproved(true);
        drinkRepository.update(drinkId, drink);

        return drink;
    }


    public Drink rejectDrinkProposal(long drinkId) {
        Drink drink = drinkRepository.findDrinkById(drinkId);
        drinkRepository.delete(drinkId);
        return drink;
    }

    public Drink setApprovedEditedDrink(long drinkId) {
        Drink newDrink = drinkRepository.findDrinkById(drinkId);
        statisticsRepositoryBean.deleteStatisticsByDrink(newDrink);

        Long newDrinkParentId = newDrink.getParentId();
        Drink oldDrink = drinkRepository.findDrinkById(newDrinkParentId);
        statisticsRepositoryBean.deleteStatisticsByDrink(oldDrink);

        newDrink.setApproved(true);
        newDrink.setId(newDrinkParentId);

        drinkRepository.update(newDrink.getId(), newDrink);
        drinkRepository.delete(drinkId);

        return newDrink;
    }

    public Drink setApprovedDeleteDrink(long drinkId) {
        Drink drink= drinkRepository.findDrinkById(drinkId);
        Long idTobeDeleted = drink.getParentId();
        Drink drinkTobeDeleted = drinkRepository.findDrinkById(idTobeDeleted);

        statisticsRepositoryBean.deleteStatisticsByDrink(drinkTobeDeleted);
        ratingRepository.removeRating(idTobeDeleted);

        drinkRepository.delete(drinkId);
        drinkRepository.delete(idTobeDeleted);

        return drink;
    }
}
