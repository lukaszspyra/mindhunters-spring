package com.spyrka.mindhunters.service.mapper;

import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.DrinkIngredient;
import com.spyrka.mindhunters.model.Ingredient;
import com.spyrka.mindhunters.model.Measure;
import com.spyrka.mindhunters.model.json.IngredientJson;
import com.spyrka.mindhunters.service.IngredientService;
import com.spyrka.mindhunters.service.MeasureService;
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
