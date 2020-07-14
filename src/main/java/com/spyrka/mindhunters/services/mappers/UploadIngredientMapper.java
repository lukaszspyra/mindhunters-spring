package com.spyrka.mindhunters.services.mappers;

import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.DrinkIngredient;
import com.spyrka.mindhunters.models.Ingredient;
import com.spyrka.mindhunters.models.Measure;
import com.spyrka.mindhunters.models.json.IngredientJson;
import com.spyrka.mindhunters.services.IngredientService;
import com.spyrka.mindhunters.services.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadIngredientMapper {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MeasureService measureService;

    public DrinkIngredient toEntity(IngredientJson ingredientJson, Drink drink) {
        Measure measure = measureService.getOrCreate(ingredientJson.getMeasure());
        Ingredient ingredient = ingredientService.getOrCreate(ingredientJson.getName());
        DrinkIngredient drinkIngredient = new DrinkIngredient();
        drinkIngredient.setIngredient(ingredient);
        drinkIngredient.setMeasure(measure);
        return drinkIngredient;
    }
}
