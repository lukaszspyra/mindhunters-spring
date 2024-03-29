package com.spyrka.mindhunters.service.mapper;


import com.spyrka.mindhunters.model.DrinkIngredient;
import com.spyrka.mindhunters.model.Ingredient;
import com.spyrka.mindhunters.model.Measure;
import com.spyrka.mindhunters.model.dto.DrinkIngredientView;
import com.spyrka.mindhunters.service.IngredientService;
import com.spyrka.mindhunters.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DrinkIgredientMapper {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MeasureService measureService;


    public DrinkIngredientView toView(DrinkIngredient drinkIngredient) {
        DrinkIngredientView view = new DrinkIngredientView(
                drinkIngredient.getIngredient().getName(),
                drinkIngredient.getMeasure().getQuantity()
        );
        return view;
    }

    public List<DrinkIngredientView> toView(List<DrinkIngredient> drinkIngredients) {
        List<DrinkIngredientView> drinkIngredientViews = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : drinkIngredients) {
            drinkIngredientViews.add(toView(drinkIngredient));
        }
        return drinkIngredientViews;
    }

    public DrinkIngredient toEntity(DrinkIngredientView drinkIngredientView) {
        DrinkIngredient drinkIngredient = new DrinkIngredient();

        Ingredient ingredient = ingredientService.getOrCreate(drinkIngredientView.getName());
        Measure measure = measureService.getOrCreate(drinkIngredientView.getMeasure());

        drinkIngredient.setIngredient(ingredient);

        drinkIngredient.setMeasure(measure);

        return drinkIngredient;
    }

    public List<DrinkIngredient> toEntity(List<DrinkIngredientView> drinkIngredientViews) {
        List<DrinkIngredient> drinkIngredients = new ArrayList<>();
        for (DrinkIngredientView drinkIngredientView : drinkIngredientViews) {
            drinkIngredients.add(toEntity(drinkIngredientView));
        }
        return drinkIngredients;
    }

}
