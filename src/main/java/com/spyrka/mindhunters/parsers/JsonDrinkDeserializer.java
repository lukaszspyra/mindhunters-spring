package com.spyrka.mindhunters.parsers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.spyrka.mindhunters.models.json.DrinkJson;
import com.spyrka.mindhunters.models.json.IngredientJson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class JsonDrinkDeserializer extends JsonDeserializer<DrinkJson> {


    @Override
    public DrinkJson deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        DrinkJson drink = new DrinkJson();

        JsonNode readValueAsTree = jsonParser.readValueAsTree();

        drink.setDrinkId(readValueAsTree.get("idDrink").asText());
        drink.setDrinkName(readValueAsTree.get("strDrink").asText());
        drink.setCategoryName(readValueAsTree.get("strCategory").asText());
        drink.setAlcoholStatus(readValueAsTree.get("strAlcoholic").asText());
        drink.setRecipe(readValueAsTree.get("strInstructions").asText());
        drink.setImageUrl(readValueAsTree.get("strDrinkThumb").asText());

        String dateAsString = readValueAsTree.get("dateModified").asText();
        if (!dateAsString.equals("null")) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime formatDateTime = LocalDateTime.parse(dateAsString, dateFormatter);
            drink.setModifiedDate(formatDateTime);
        } else {
            LocalDateTime formatDateTime = LocalDateTime.now();
            drink.setModifiedDate(formatDateTime);
        }

        IngredientJson ingredient = new IngredientJson();

        List<IngredientJson> ingredientsList = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            String ingredientMeasureField = "strMeasure" + i;
            String ingredientNameField = "strIngredient" + i;

            if (!readValueAsTree.get(ingredientNameField).asText().equals("null") && !readValueAsTree.get(ingredientNameField).asText().isEmpty()) {
                if (!readValueAsTree.get(ingredientMeasureField).asText().equals("null")) {
                    ingredient.setName(readValueAsTree.get(ingredientNameField).asText());
                    ingredient.setMeasure(readValueAsTree.get(ingredientMeasureField).asText());
                    ingredientsList.add(ingredient);
                    ingredient = new IngredientJson();
                } else {
                    ingredient.setName(readValueAsTree.get(ingredientNameField).asText());
                    ingredient.setMeasure("no measures");
                    ingredientsList.add(ingredient);
                    ingredient = new IngredientJson();
                }

            } else break;
            drink.setIngredients(ingredientsList);
        }


        return drink;
    }
}