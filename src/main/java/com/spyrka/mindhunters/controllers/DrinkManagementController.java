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
        Map<String, String[]> parameterMap = req.getParameterMap();

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


//
//@WebServlet("/drink-management")
//@MultipartConfig
//public class DrinkManagementServlet extends HttpServlet {
//
//    private static final Logger packageLogger = LoggerFactory.getLogger(DrinkManagementServlet.class.getName());
//
//    @Inject
//    ImageUploadProcessor imageUploadProcessor;
//
//    @Inject
//    private TemplateProvider templateProvider;
//
//    @EJB
//    private DrinkService drinkService;
//
//    @EJB
//    private CategoryService categoryService;
//
//    @EJB
//    private IngredientService ingredientService;
//
//    @EJB
//    private MeasureService measureService;
//
//    @Inject
//    private UserInputValidator userInputValidator;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String action = req.getParameter("action");
//
//        if (action == null || action.isBlank()) {
//            action = "";
//        }
//
//        String idParam = req.getParameter("id");
//        Long drinkId = userInputValidator.stringToLongConverter(idParam);
//
//        resp.setContentType("text/html; charset=UTF-8");
//
//        Map<String, Object> dataModel = new HashMap<>();
//        Template template = null;
//
//        ContextHolder contextHolder = new ContextHolder(req.getSession());
//        dataModel.put("name", contextHolder.getName());
//        dataModel.put("role", contextHolder.getRole());
//
//        FullDrinkView drinkView = drinkService.getFullDrinkViewById(drinkId);
//
//        dataModel.put("drink", drinkView);
//        dataModel.put("categories", categoryService.findAllCategories());
//        template = templateProvider.getTemplate(getServletContext(), "editDrinkForm.ftlh");
//
//        try {
//            template.process(dataModel, resp.getWriter());
//        } catch (
//                TemplateException e) {
//            packageLogger.error(e.getMessage());
//        }
//
//    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("action");
//        String id = req.getParameter("id");
//
//        Drink drink = new Drink();
//
//        Category category = categoryService.getOrCreate(req.getParameter("category"));
//
//
//        String[] measures = req.getParameterValues("measure");
//        String[] ingredients = req.getParameterValues("ingredient");
//
//        List<Measure> measureList = new ArrayList<>();
//        List<Ingredient> ingredientList = new ArrayList<>();
//
//
//        for (String measure : measures) {
//            measureList.add(measureService.getOrCreate(measure));
//        }
//        for (String ingredient : ingredients) {
//            ingredientList.add(ingredientService.getOrCreate(ingredient));
//        }
//
//        List<DrinkIngredient> drinkIngredientsList = new ArrayList<>();
//
//        for (int i = 0; i < measureList.size(); i++) {
//            DrinkIngredient drinkIngredient = new DrinkIngredient();
//
//            drinkIngredient.setMeasure(measureList.get(i));
//            drinkIngredient.setIngredient(ingredientList.get(i));
//            drinkIngredient.setDrinkId(drink);
//
//            drinkIngredientsList.add(drinkIngredient);
//        }
//        ContextHolder contextHolder = new ContextHolder(req.getSession());
//
//        drink.setConfirmUserEmail(contextHolder.getEmail());
//
//
//        drink.setDrinkIngredients(drinkIngredientsList);
//        drink.setDrinkName(req.getParameter("name"));
//        drink.setRecipe(req.getParameter("recipe"));
//        drink.setCategory(category);
//        drink.setAlcoholStatus(req.getParameter("status"));
//        drink.setDate(LocalDateTime.now());
//        drink.setApproved(false);
//        drink.setManageAction("ADD");
//        Part image = req.getPart("image");
//        String imageUrl = "";
//        try {
//            imageUrl = "/pictures/" + imageUploadProcessor
//                    .uploadImageFile(image).getName();
//        } catch (JsonNotFound userImageNotFound) {
//            packageLogger.warn(userImageNotFound.getMessage());
//        }
//
//        drink.setImage(imageUrl);
//        if (action != null && id != null) {
//            drinkService.addOrUpdate(Long.valueOf(id), drink);
//            resp.sendRedirect("/single-view?drink=" + id);
//            return;
//        }
//        drinkService.save(drink);
//
//
//        resp.sendRedirect("/list?page=1");
//
//        return;
//
//
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//
//        resp.getWriter().print("aaaa");
//        resp.sendRedirect("/list?page=1");
//
//        return;
//    }
//}
