package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.jsonsupport.JsonCategoryApiReader;
import com.spyrka.mindhunters.jsonsupport.JsonParserApiBean;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.json.CategoryJson;
import com.spyrka.mindhunters.models.json.DrinkJson;
import com.spyrka.mindhunters.services.DrinkService;
import com.spyrka.mindhunters.services.JsonService;
import com.spyrka.mindhunters.services.mappers.UploadDrinkMapper;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller("/admin/upload-json-api")
public class UploadDbFromApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDbFromApiController.class.getName());

    @Autowired
    private UploadDrinkMapper uploadDrinkMapper;

    @Autowired
    private JsonService jsonService;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private JsonParserApiBean jsonParserApiBean;

    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<DrinkJson> drinkJsons = new ArrayList<>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            Request fromAlphabet = Request.Get("http://isa-proxy.blueazurit.com/cocktails/1/search.php?f=" + alphabet);
            String stringDrinkJson = fromAlphabet.execute().returnContent().asString();

            List<DrinkJson> letterDrinkJsons = Optional.ofNullable(jsonParserApiBean.jsonDrinkReaderFromString(stringDrinkJson))
                    .orElse(Collections.emptyList());
            for (DrinkJson letterDrinkJson : letterDrinkJsons) {
                drinkJsons.add(letterDrinkJson);
            }
        }

        Request getCat = Request.Get("http://isa-proxy.blueazurit.com/cocktails/1/list.php?c=list");
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
