package com.spyrka.mindhunters.service.mapper;

import com.spyrka.mindhunters.model.Category;
import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.DrinkIngredient;
import com.spyrka.mindhunters.model.json.CategoryJson;
import com.spyrka.mindhunters.model.json.DrinkJson;
import com.spyrka.mindhunters.model.json.IngredientJson;
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
