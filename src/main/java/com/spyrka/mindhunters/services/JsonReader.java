package com.spyrka.mindhunters.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spyrka.mindhunters.models.json.DrinkJson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public class JsonReader {

    private JsonReader() {
    }

    public static List<DrinkJson> jsonDrinkReader(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        File json = new File(fileName);
        JsonNode jsonNode = mapper.readTree(json);


        List<DrinkJson> drink = (List<DrinkJson>) mapper.readValue(jsonNode.get("drinks").toString(),
                new TypeReference<List<DrinkJson>>() {
                });

        return drink;
    }


    public static Set<String> jsonFavouritesReader(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File json = new File(fileName);
        final Set<String> favouritesSet = mapper.readValue(json, FavouritesDatabase.class).getFavouritesIds();

        return favouritesSet;
    }


}