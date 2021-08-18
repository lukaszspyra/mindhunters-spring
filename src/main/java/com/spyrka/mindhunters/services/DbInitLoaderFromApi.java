package com.spyrka.mindhunters.services;

import com.spyrka.mindhunters.jsonsupport.JsonCategoryApiReader;
import com.spyrka.mindhunters.jsonsupport.JsonParserApiBean;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.json.CategoryJson;
import com.spyrka.mindhunters.models.json.DrinkJson;
import com.spyrka.mindhunters.services.mappers.UploadDrinkMapper;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Scope("singleton")
public class DbInitLoaderFromApi {

    private static final Logger packageLogger = LoggerFactory.getLogger(DbInitLoaderFromApi.class.getName());

    private final String API_URL_DRINKS = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=";

    private final String API_URL_CATEGORIES = "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list";

    @Autowired
    private UploadDrinkMapper uploadDrinkMapper;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private JsonParserApiBean jsonParserApiBean;

    @PostConstruct
    public void loadApi() {


        List<DrinkJson> drinkJsons = new ArrayList<>();
        List<CategoryJson> categoryJson = new ArrayList<>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            Request fromAlphabet = Request.Get(API_URL_DRINKS + alphabet);
            String stringDrinkJson = null;
            try {
                stringDrinkJson = fromAlphabet.execute().returnContent().asString();
            } catch (IOException e) {
                packageLogger.error("DrinkJson not found");
            }

            List<DrinkJson> letterDrinkJsons = null;
            try {
                letterDrinkJsons = Optional.ofNullable(jsonParserApiBean.jsonDrinkReaderFromString(stringDrinkJson))
                        .orElse(Collections.emptyList());
            } catch (IOException e) {
                packageLogger.error("letterDrinkJson not found");

            }
            for (DrinkJson letterDrinkJson : letterDrinkJsons) {
                drinkJsons.add(letterDrinkJson);
            }
        }

        Request getCat = Request.Get(API_URL_CATEGORIES);
        String stringCatDrinkJson = null;
        try {
            stringCatDrinkJson = getCat.execute().returnContent().asString();
        } catch (IOException e) {
            packageLogger.error("stringCatDrinkJson not found");
        }
        try {
            categoryJson = JsonCategoryApiReader.jsonCategoryReader(stringCatDrinkJson);
        } catch (IOException e) {
            packageLogger.error("categoryJson not found");
        }
        Drink drink = new Drink();
        for (DrinkJson drinkJson : drinkJsons) {

            drink = uploadDrinkMapper.toEntity(drinkJson, categoryJson.get(1));
            drinkService.save(drink);
        }
    }
}
