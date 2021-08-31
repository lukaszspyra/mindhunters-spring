package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.jsonsupport.JsonCategoryApiReader;
import com.spyrka.mindhunters.jsonsupport.JsonParserApiBean;
import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.json.CategoryJson;
import com.spyrka.mindhunters.model.json.DrinkJson;
import com.spyrka.mindhunters.service.DrinkService;
import com.spyrka.mindhunters.service.mapper.UploadDrinkMapper;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/upload-json-api")
public class UploadDbFromApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDbFromApiController.class.getName());

    private final String API_URL_DRINKS = "https://www.thecocktaildb.com/api/json/v1/1/search.php?f=";

    private final String API_URL_CATEGORIES = "https://www.thecocktaildb.com/api/json/v1/1/list.php?c=list";

    @Autowired
    private UploadDrinkMapper uploadDrinkMapper;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private JsonParserApiBean jsonParserApiBean;

    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<DrinkJson> drinkJsons = new ArrayList<>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            Request fromAlphabet = Request.Get(API_URL_DRINKS + alphabet);
            String stringDrinkJson = fromAlphabet.execute().returnContent().asString();

            List<DrinkJson> letterDrinkJsons = Optional.ofNullable(jsonParserApiBean.jsonDrinkReaderFromString(stringDrinkJson))
                    .orElse(Collections.emptyList());
            for (DrinkJson letterDrinkJson : letterDrinkJsons) {
                drinkJsons.add(letterDrinkJson);
            }
        }

        Request getCat = Request.Get(API_URL_CATEGORIES);
        String stringCatDrinkJson = getCat.execute().returnContent().asString();
        List<CategoryJson> categoryJson = new ArrayList<>();
        categoryJson = JsonCategoryApiReader.jsonCategoryReader(stringCatDrinkJson);

        Drink drink = new Drink();
        for (DrinkJson drinkJson : drinkJsons) {
            drink = uploadDrinkMapper.toEntity(drinkJson, categoryJson.get(1));
            drinkService.save(drink);
        }
        resp.sendRedirect("/admin/page");
    }
}
