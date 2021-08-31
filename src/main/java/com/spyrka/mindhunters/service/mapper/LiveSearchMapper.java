package com.spyrka.mindhunters.service.mapper;


import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.Ingredient;
import com.spyrka.mindhunters.model.dto.DrinkLiveSearchView;
import com.spyrka.mindhunters.model.dto.IngredientView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LiveSearchMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveSearchMapper.class.getName());

    public List<DrinkLiveSearchView> toView(List<Drink> drinks) {
        List<DrinkLiveSearchView> drinkViews = new ArrayList<>();
        for (Drink drink : drinks) {
            drinkViews.add(toView(drink));
        }
        return drinkViews;
    }

    public DrinkLiveSearchView toView(Drink drink) {
        DrinkLiveSearchView drinkView = new DrinkLiveSearchView();
        drinkView.setId(drink.getId());
        drinkView.setDrinkId(drink.getDrinkId());
        drinkView.setDrinkName(drink.getDrinkName());
        return drinkView;
    }

    public IngredientView ingredientToView(Ingredient ingredient) {
        IngredientView ingredientView = new IngredientView();
        ingredientView.setId(ingredient.getId());
        ingredientView.setName(ingredient.getName());
        return ingredientView;
    }

    public List<IngredientView> ingredientsToView(List<Ingredient> ingredients) {
        List<IngredientView> ingredientViews = new ArrayList<>();
        for (Ingredient i : ingredients) {
            ingredientViews.add(ingredientToView(i));
        }
        return ingredientViews;
    }
}
