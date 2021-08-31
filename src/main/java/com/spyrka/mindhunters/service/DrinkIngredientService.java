package com.spyrka.mindhunters.service;

import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.DrinkIngredient;
import com.spyrka.mindhunters.model.Ingredient;
import com.spyrka.mindhunters.model.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrinkIngredientService {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MeasureService measureService;

    public List<DrinkIngredient> getOrCreate(Drink submittedDrink) {
        List<DrinkIngredient> drinkIngredients = new ArrayList<>();
        for (DrinkIngredient submittedDrinkIngredient : submittedDrink.getDrinkIngredients()){
            if(submittedDrinkIngredient == null){
                continue;
            }
            Ingredient ingredient = ingredientService.getOrCreate(submittedDrinkIngredient.getIngredient().getName());
            Measure measure = measureService.getOrCreate(submittedDrinkIngredient.getMeasure().getQuantity());
            DrinkIngredient drinkIngredient = new DrinkIngredient();
            drinkIngredient.setIngredient(ingredient);
            drinkIngredient.setMeasure(measure);
            drinkIngredient.setDrinkId(submittedDrink);
            drinkIngredients.add(drinkIngredient);
        }
        return drinkIngredients;
    }
}
