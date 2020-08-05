package com.spyrka.mindhunters.services.mappers;

import com.spyrka.mindhunters.models.Category;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.DrinkIngredient;
import com.spyrka.mindhunters.models.json.CategoryJson;
import com.spyrka.mindhunters.models.json.DrinkJson;
import com.spyrka.mindhunters.models.json.IngredientJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UploadDrinkMapper {

    @Autowired
    UploadCategoryMapper uploadCategoryMapper;

    @Autowired
    UploadIngredientMapper uploadIngredientMapper;

    public Drink toEntity(DrinkJson drinkJson, CategoryJson categoryJson) {

        Drink drink = new Drink();

        String categoryName = drinkJson.getCategoryName();

        drink.setDrinkId(drinkJson.getDrinkId());
        drink.setDrinkName(drinkJson.getDrinkName());

        drink.setAlcoholStatus(drinkJson.getAlcoholStatus());
        drink.setRecipe(drinkJson.getRecipe());
        drink.setImage(drinkJson.getImageUrl());
        drink.setDate(drinkJson.getModifiedDate());
        drink.setApproved(true);
        
        List<DrinkIngredient> drinkIngredients = new ArrayList<>();
        for (IngredientJson ingredientJson : drinkJson.getIngredients()) {
            DrinkIngredient drinkIngredient = uploadIngredientMapper.toEntity(ingredientJson, drink);
            drinkIngredient.setDrinkId(drink);
            drinkIngredients.add(drinkIngredient);
        }
        drink.setDrinkIngredients(drinkIngredients);
        categoryJson.setCategoryName(drinkJson.getCategoryName());
        Category category = uploadCategoryMapper.toEntity(categoryJson);
        drink.setCategory(category);

        return drink;
    }
}
