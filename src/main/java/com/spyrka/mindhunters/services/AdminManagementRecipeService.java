package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.*;
import com.spyrka.mindhunters.repositories.DrinkRepository;
import com.spyrka.mindhunters.repositories.RatingRepository;
import com.spyrka.mindhunters.repositories.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    /**
     * Creates deep copy of existing drink, proposed by the user to be deleted.
     *
     * @implNote Uses method setDrinkIngredients() to deep copy drinkIngredients objects.
     */
    @Transactional
    public boolean proposeDeleteDrink(Long id, String email) {
        Optional<Drink> existingDrinkOptional = drinkRepository.findById(id);
        if (existingDrinkOptional.isEmpty()) {
            return false;
        }
        Drink existingDrink = existingDrinkOptional.get();
        Drink drinkToBeDeleted = new Drink();

        drinkToBeDeleted.setDrinkName(existingDrink.getDrinkName());
        drinkToBeDeleted.setCategory(existingDrink.getCategory());
        drinkToBeDeleted.setAlcoholStatus(existingDrink.getAlcoholStatus());
        drinkToBeDeleted.setRecipe(existingDrink.getRecipe());

        existingDrink.setDrinkIngredients(copyDrinkIngredients(existingDrink, drinkToBeDeleted));

        drinkToBeDeleted.setParentId(id);
        drinkToBeDeleted.setManageAction("DELETE");
        drinkToBeDeleted.setImage(existingDrink.getImage());

        drinkToBeDeleted.setDate(LocalDateTime.now());
        drinkToBeDeleted.setApproved(false);
        drinkToBeDeleted.setConfirmUserEmail(email);

        drinkRepository.save(drinkToBeDeleted);
        return true;
    }

    private List<DrinkIngredient> copyDrinkIngredients(Drink fromDrink, Drink toDrink) {
        List<Measure> measures = fromDrink.getDrinkIngredients().stream()
                .filter(Objects::nonNull)
                .map(drinkIngredient -> measureService
                        .getOrCreate(drinkIngredient.getMeasure().getQuantity()))
                .collect(Collectors.toList());

        List<Ingredient> ingredients = fromDrink.getDrinkIngredients().stream()
                .filter(Objects::nonNull)
                .map(drinkIngredient1 -> ingredientService
                        .getOrCreate(drinkIngredient1.getIngredient().getName()))
                .collect(Collectors.toList());

        List<DrinkIngredient> drinkIngredientsList = new ArrayList<>();

        for (int i = 0; i < measures.size(); i++) {
            DrinkIngredient drinkIngredient = new DrinkIngredient();

            drinkIngredient.setMeasure(measures.get(i));
            drinkIngredient.setIngredient(ingredients.get(i));

            drinkIngredient.setDrinkId(toDrink);

            drinkIngredientsList.add(drinkIngredient);
        }
        return drinkIngredientsList;
    }

    public Drink setApprovedDeleteDrink(long approvedDrinkId) {
        Drink approvedDrink = drinkRepository.findById(approvedDrinkId).orElseThrow();
        drinkRepository.deleteIngredientsFromDrink(approvedDrinkId);
        drinkRepository.delete(approvedDrink);

        Long parentIdTobeDeleted = approvedDrink.getParentId();
        deleteDrinkById(parentIdTobeDeleted);

        return approvedDrink;
    }

    @Transactional
    public boolean deleteDrinkById(Long idToBeDeleted) {
        Optional<Drink> optDrinkToBeDeleted = drinkRepository.findById(idToBeDeleted);
        if (optDrinkToBeDeleted.isEmpty()) {
            return false;
        }
        Drink drinkToBeDeleted = optDrinkToBeDeleted.get();

        List<User> users = drinkToBeDeleted.getUsers();
        for (User user : users) {
            user.getDrinks().remove(drinkToBeDeleted);
        }
        statisticsRepository.deleteStatisticsByDrink(drinkToBeDeleted);
        ratingRepository.findByDrinkId(idToBeDeleted).ifPresent(rating1 -> ratingRepository.delete(rating1));
        drinkRepository.deleteIngredientsFromDrink(idToBeDeleted);
        drinkRepository.delete(drinkToBeDeleted);
        return true;
    }

    @Transactional
    public boolean addOrUpdateDrink(Long id, Drink submittedDrink) {
        Drink newDrink = drinkRepository.findById(id).orElse(new Drink());

        if (submittedDrink != null) {
            newDrink.setDrinkName(submittedDrink.getDrinkName());
            newDrink.setConfirmUserEmail(submittedDrink.getConfirmUserEmail());
            newDrink.setAlcoholStatus(submittedDrink.getAlcoholStatus());
            newDrink.setRecipe(submittedDrink.getRecipe());

            List<DrinkIngredient> drinkIngredientsList = copyDrinkIngredients(submittedDrink, newDrink);
            newDrink.getDrinkIngredients().clear();//TODO: czy potrzebne? raczej nie
            newDrink.setDrinkIngredients(drinkIngredientsList);

            newDrink.setImage(submittedDrink.getImage());
            LocalDateTime formatDateTime = LocalDateTime.now();
            newDrink.setDate(formatDateTime);

            Category category = categoryService.getOrCreate(submittedDrink.getCategory().getName());
            newDrink.setCategory(category);
        }

        if (id != 0L) {
            newDrink.setManageAction("EDIT");
            //drinkRepository.deleteIngredientsFromDrink(id); TODO:check this
        } else {
            newDrink.setManageAction("ADD");
        }
        newDrink.setApproved(false);
        drinkRepository.save(newDrink);
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
        drinkRepository.deleteIngredientsFromDrink(oldDrink.getId());
        statisticsRepository.deleteStatisticsByDrink(oldDrink);//TODO: check this

        drinkRepository.delete(newDrink);

        newDrink.setApproved(true);
        newDrink.setId(newDrinkParentId);
        drinkRepository.save(newDrink);
        return newDrink;
    }
}
