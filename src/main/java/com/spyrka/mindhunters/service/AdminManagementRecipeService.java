package com.spyrka.mindhunters.service;


import com.spyrka.mindhunters.model.*;
import com.spyrka.mindhunters.repository.DrinkIngredientRepository;
import com.spyrka.mindhunters.repository.DrinkRepository;
import com.spyrka.mindhunters.repository.RatingRepository;
import com.spyrka.mindhunters.repository.StatisticsRepository;
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
    public static long NEW_API_ID = 1;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private DrinkIngredientRepository drinkIngredientRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private MeasureService measureService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DrinkIngredientService drinkIngredientService;

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
        drinkToBeDeleted.setDrinkIngredients(copyDrinkIngredients(existingDrink, drinkToBeDeleted));

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

    /**
     * Removes drinks from database - original and temporary, used to save delete proposed record
     *
     * @param approvedDrinkId for removal
     * @return removed drink
     */
    @Transactional
    public Drink setApprovedDeleteDrink(long approvedDrinkId) {
        Drink approvedDrink = drinkRepository.findById(approvedDrinkId).orElseThrow();
        Long parentIdTobeDeleted = approvedDrink.getParentId();
        drinkIngredientRepository.deleteIngredientsFromDrink(approvedDrinkId);
        deleteDrinkById(approvedDrinkId);

        deleteDrinkById(parentIdTobeDeleted);

        return approvedDrink;
    }


    private void deleteDrinkById(Long idToBeDeleted) {
        Optional<Drink> optDrinkToBeDeleted = drinkRepository.findById(idToBeDeleted);
        if (optDrinkToBeDeleted.isEmpty()) {
            return;
        }
        Drink drinkToBeDeleted = optDrinkToBeDeleted.get();

        List<User> users = drinkToBeDeleted.getUsers();
        for (User user : users) {
            user.getDrinks().remove(drinkToBeDeleted);
        }
        statisticsRepository.deleteStatisticsByDrink(drinkToBeDeleted);
        ratingRepository.findByDrinkId(idToBeDeleted).ifPresent(rating1 -> ratingRepository.delete(rating1));
        drinkIngredientRepository.deleteIngredientsFromDrink(idToBeDeleted);
        drinkRepository.delete(drinkToBeDeleted);
    }

    private boolean prepareDrinkProposalForPersisting(Drink submittedDrink, String manageAction) {
        submittedDrink.setManageAction(manageAction);
        submittedDrink.setDate(LocalDateTime.now());
        Category category = categoryService.getOrCreate(submittedDrink.getCategory().getName());
        submittedDrink.setCategory(category);
        submittedDrink.setDrinkId(String.valueOf(NEW_API_ID));
        increaseApiId();

        List<DrinkIngredient> drinkIngredients = drinkIngredientService.getOrCreate(submittedDrink);
        submittedDrink.setDrinkIngredients(drinkIngredients);
        drinkRepository.save(submittedDrink);
        return true;
    }

    @Transactional
    public boolean updateDrink(Long existingId, Drink submittedDrink) {
        Optional<Drink> optExistingDrink = drinkRepository.findById(existingId);
        if (optExistingDrink.isEmpty() || submittedDrink == null) {
            return false;
        }
        submittedDrink.setParentId(existingId);
        return prepareDrinkProposalForPersisting(submittedDrink, "EDIT");
    }

    public static void increaseApiId() {
        NEW_API_ID++;
    }

    @Transactional
    public Drink approveNewDrinkCreation(long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow();
        drink.setApproved(true);
        drink.setManageAction(null);
        return drinkRepository.save(drink);
    }

    @Transactional
    public Drink approveDrinkUpdate(long updatedDrinkId) {
        Drink approvedSubmittedUpdate = drinkRepository.findById(updatedDrinkId).orElseThrow();
        Long parentId = approvedSubmittedUpdate.getParentId();
        deleteDrinkById(parentId);
        approvedSubmittedUpdate.setApproved(true);
        approvedSubmittedUpdate.setManageAction(null);
        approvedSubmittedUpdate.setParentId(null);
        return drinkRepository.save(approvedSubmittedUpdate);
    }

    @Transactional
    public Drink rejectDrinkProposal(long drinkId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow();
        deleteDrinkById(drinkId);
        return drink;
    }
}
