package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.json.DrinkJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JsonService {

    private static final Logger packageLogger = LoggerFactory.getLogger(JsonService.class.getName());

    public void save(String pathToJsonFile) {
        List<DrinkJson> drinksFromJson = filerToListOfDrinksJson(pathToJsonFile);
    }

    private List<DrinkJson> filerToListOfDrinksJson(String pathToJsonFile) {
        try {
            return JsonReader.jsonDrinkReader(pathToJsonFile);
        } catch (IOException jsonNotFound) {
            packageLogger.error(jsonNotFound.getMessage());
        }
        return List.of();
    }
}
