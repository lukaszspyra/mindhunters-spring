package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.exceptions.JsonNotFound;
import com.spyrka.mindhunters.jsonsupport.FileUploadProcessor;
import com.spyrka.mindhunters.jsonsupport.JsonCategoryReader;
import com.spyrka.mindhunters.jsonsupport.JsonParserBean;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.json.CategoryJson;
import com.spyrka.mindhunters.models.json.DrinkJson;
import com.spyrka.mindhunters.services.DrinkService;
import com.spyrka.mindhunters.services.JsonService;
import com.spyrka.mindhunters.services.mappers.UploadDrinkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

//@MultipartConfig
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

    @GetMapping("/admin/upload-json-file")
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String filename = URLDecoder.decode(req.getPathInfo().substring(1), "UTF-8");
        File file = new File(fileUploadProcessor.getUploadJsonFilesPath(), filename);
        resp.setHeader("Content-Type", Files.probeContentType(file.toPath()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }

    @PostMapping("/admin/upload-json-file")
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
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
