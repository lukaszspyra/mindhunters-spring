package com.spyrka.mindhunters.model.dto;

import java.util.ArrayList;
import java.util.List;

public class SimpleDrinkView {

    private Long id;

    private String drinkId;

    private String drinkName;

    private String alcoholStatus;

    private String recipe;

    private List<DrinkIngredientView> drinkIngredientViews = new ArrayList<>();

    private CategoryView categoryView;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getAlcoholStatus() {
        return alcoholStatus;
    }

    public void setAlcoholStatus(String alcoholStatus) {
        this.alcoholStatus = alcoholStatus;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public List<DrinkIngredientView> getDrinkIngredientViews() {
        return drinkIngredientViews;
    }

    public void setDrinkIngredientViews(List<DrinkIngredientView> drinkIngredientViews) {
        this.drinkIngredientViews = drinkIngredientViews;
    }

    public CategoryView getCategoryView() {
        return categoryView;
    }

    public void setCategoryView(CategoryView categoryView) {
        this.categoryView = categoryView;
    }
}
