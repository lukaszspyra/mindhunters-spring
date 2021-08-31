package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.exception.JsonNotFound;
import com.spyrka.mindhunters.jsonsupport.FileUploadProcessor;
import com.spyrka.mindhunters.jsonsupport.JsonCategoryReader;
import com.spyrka.mindhunters.jsonsupport.JsonParserBean;
import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.json.CategoryJson;
import com.spyrka.mindhunters.model.json.DrinkJson;
import com.spyrka.mindhunters.service.DrinkService;
import com.spyrka.mindhunters.service.JsonService;
import com.spyrka.mindhunters.service.mapper.UploadDrinkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin/upload-json-file")
public class UploadDbFromFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadDbFromFileController.class.getName());

    @Autowired
    private UploadDrinkMapper uploadDrinkMapper;

    @Autowired
    private JsonService jsonService;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private FileUploadProcessor fileUploadProcessor;

    @Autowired
    private JsonParserBean jsonParserBean;


    @PostMapping()
    public void doPost(MultipartHttpServletRequest req, HttpServletResponse resp, RedirectAttributes attributes)
            throws IOException, ServletException {
        Part jsonPath = req.getPart("jsonFile");
        List<DrinkJson> drinkJsons = new ArrayList<>();
        try {
            drinkJsons = jsonParserBean.jsonDrinkReader(fileUploadProcessor.uploadJsonFile(jsonPath));
        } catch (JsonNotFound jsonNotFound) {
            LOGGER.error(jsonNotFound.getMessage());
        }

        Part jsonCatPath = req.getPart("jsonCatFile");
        List<CategoryJson> categoryJson = new ArrayList<>();
        try {
            categoryJson = JsonCategoryReader.jsonCategoryReader(fileUploadProcessor.uploadJsonFile(jsonCatPath).getPath());
        } catch (JsonNotFound jsonNotFound) {
            LOGGER.error(jsonNotFound.getMessage());
        }

        Drink drink = new Drink();
        for (DrinkJson drinkJson : drinkJsons) {
            drink = uploadDrinkMapper.toEntity(drinkJson, categoryJson.get(1));
            drinkService.save(drink);
        }
        resp.sendRedirect("/admin/page");
    }

}
