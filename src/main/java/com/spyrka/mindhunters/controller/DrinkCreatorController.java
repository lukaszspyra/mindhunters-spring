package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.exception.JsonNotFound;
import com.spyrka.mindhunters.model.*;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.service.*;
import com.spyrka.mindhunters.service.imagefileupload.ImageUploadProcessor;
import com.spyrka.mindhunters.service.validator.UserInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles recipes creation from empty or existing form
 */
@Controller
@RequestMapping("/drink-management")
public class DrinkCreatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkCreatorController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MeasureService measureService;

    @Autowired
    private ImageUploadProcessor imageUploadProcessor;

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;


    /**
     * Displays form prefilled with {@link Drink} details.
     *
     * @param model
     * @param req
     * @param resp
     * @return
     */
    @GetMapping
    public String editForm(Model model, HttpServletRequest req, HttpServletResponse resp) {
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

    /**
     * Displays form for {@link Drink} creation
     *
     * @param model
     * @param req
     * @param resp
     * @return
     */
    @GetMapping("/create")
    public String createForm(Model model, HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html; charset=UTF-8");

        Map<String, Object> dataModel = new HashMap<>();

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());
        dataModel.put("categories", categoryService.findAllCategories());

        model.addAllAttributes(dataModel);
        LOGGER.info("Model sent to view");

        return "addDrinkForm";
    }

    /**
     * Receives new {@link Drink} proposal from recipe creating form
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping("/create")
    protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        Drink submittedDrink = new Drink();

        submittedDrink.setDrinkName(req.getParameter("drinkName"));
        submittedDrink.setManageAction("ADD");
        submittedDrink.setDate(LocalDateTime.now());
        submittedDrink.setAlcoholStatus(req.getParameter("alcoholStatus"));
        submittedDrink.setApproved(false);
        submittedDrink.setConfirmUserEmail(contextHolder.getEmail());
        submittedDrink.setRecipe(req.getParameter("recipe"));
        submittedDrink.setDrinkId(String.valueOf(AdminManagementRecipeService.NEW_API_ID));
        AdminManagementRecipeService.increaseApiId();

        Category category = categoryService.getOrCreate(req.getParameter("category[name]"));
        submittedDrink.setCategory(category);


        String[] measures = req.getParameterValues("drinkIngredients[0][measure[quantity]]");
        String[] ingredients = req.getParameterValues("drinkIngredients[0][ingredient[name]]");

        List<Measure> measureList = Arrays.stream(measures)
                .map(m -> measureService.getOrCreate(m))
                .collect(Collectors.toList());

        List<Ingredient> ingredientList = Arrays.stream(ingredients)
                .map(ing -> ingredientService.getOrCreate(ing))
                .collect(Collectors.toList());

        List<DrinkIngredient> drinkIngredientList = new ArrayList<>();

        for (int i = 0; i < measureList.size(); i++) {
            DrinkIngredient drinkIngredient = new DrinkIngredient();

            drinkIngredient.setMeasure(measureList.get(i));
            drinkIngredient.setIngredient(ingredientList.get(i));
            drinkIngredient.setDrinkId(submittedDrink);

            drinkIngredientList.add(drinkIngredient);
        }
        submittedDrink.setDrinkIngredients(drinkIngredientList);

        Part image = req.getPart("image");
        String imageUrl = "";
        try {
            imageUrl = "/pictures/" + imageUploadProcessor
                    .uploadImageFile(image).getName();
        } catch (JsonNotFound userImageNotFound) {
            LOGGER.warn(userImageNotFound.getMessage());
        }
        submittedDrink.setImage(imageUrl);

        drinkService.save(submittedDrink);
        resp.sendRedirect("/list?page=1");

    }

}