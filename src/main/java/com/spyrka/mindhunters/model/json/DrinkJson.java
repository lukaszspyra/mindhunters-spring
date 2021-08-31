package com.spyrka.mindhunters.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties({"strDrinkAlternate", "strDrinkES", "strDrinkDE", "strDrinkFR",
        "strDrinkZH-HANS", "strDrinkZH-HANT", "strTags", "strVideo", "strIBA", "strGlass",
        "strInstructionsES", "strInstructionsDE", "strInstructionsFR", "strInstructionsZH-HANS",
        "strInstructionsZH-HANT", "strDrinkThumb", "strCreativeCommonsConfirmed"})

@JsonDeserialize(using = JsonDrinkDeserializer.class)
public class DrinkJson {

    @JsonProperty("idDrink")
    private String drinkId;
    @JsonProperty("strDrink")
    private String drinkName;
    @JsonProperty("strCategory")
    private String categoryName;
    @JsonProperty("strAlcoholic")
    private String alcoholStatus;
    @JsonProperty("strInstructions")
    private String recipe;
    @JsonProperty("strDrinkThumb")
    private String imageUrl;
    private List<IngredientJson> ingredients;
    @JsonProperty("dateModified")
    private LocalDateTime modifiedDate;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<IngredientJson> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientJson> ingredients) {
        this.ingredients = ingredients;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "drinkId='" + drinkId + '\'' +
                ", drinkName='" + drinkName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", alcoholStatus='" + alcoholStatus + '\'' +
                ", recipe='" + recipe + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", ingredients=" + ingredients +
                ", modifiedDate='" + (modifiedDate != null ? modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM" +
                "-dd " +
                "HH:mm:ss")) : null) +
                "'}";
    }

    public List<String> getIngredientsNamesList() {
        List<String> ingredientsNames = new ArrayList<>();
        for (IngredientJson ingredient : ingredients) {
            ingredientsNames.add(ingredient.getName());
        }
        return ingredientsNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrinkJson drink = (DrinkJson) o;
        return Objects.equals(drinkId, drink.drinkId) &&
                Objects.equals(drinkName, drink.drinkName) &&
                Objects.equals(categoryName, drink.categoryName) &&
                Objects.equals(alcoholStatus, drink.alcoholStatus) &&
                Objects.equals(recipe, drink.recipe) &&
                Objects.equals(imageUrl, drink.imageUrl) &&
                Objects.equals(ingredients, drink.ingredients) &&
                Objects.equals(modifiedDate, drink.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkId, drinkName, categoryName, alcoholStatus, recipe, imageUrl, ingredients, modifiedDate);
    }
}

