package com.spyrka.mindhunters.controllers;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.services.CategoryService;
import com.spyrka.mindhunters.services.DrinkService;
import com.spyrka.mindhunters.services.validator.UserInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//@MultipartConfig
@Controller
@RequestMapping("/drink-management")
public class DrinkManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkManagementController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserInputValidator userInputValidator;

    @GetMapping
    public String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        Long drinkId = userInputValidator.stringToLongConverter(idParam);

        resp.setContentType("text/html; charset=UTF-8");

        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        FullDrinkView drinkView = drinkService.getFullDrinkViewById(drinkId);

        dataModel.put("drink", drinkView);
        dataModel.put("categories", categoryService.findAllCategories());
        model.addAllAttributes(dataModel);
        LOGGER.info("Model sent to view");

        return "editDrinkForm";
    }
}
