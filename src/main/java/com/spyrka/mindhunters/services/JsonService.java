package com.spyrka.mindhunters.services;

import com.infoshareacademy.domain.DrinkJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.util.List;


@RequestScoped
public class JsonService {

    private static final Logger packageLogger = LoggerFactory.getLogger(com.infoshareacademy.service.JsonService.class.getName());

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
