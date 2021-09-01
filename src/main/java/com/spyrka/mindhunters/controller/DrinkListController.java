package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.model.dto.CategoryView;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.service.*;
import com.spyrka.mindhunters.service.validator.UserInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Displays list of all drinks from database
 * <p>
 * List is adjusted to {@link com.spyrka.mindhunters.model.User} age declaration, displaying alcoholic beverages to adults only.
 */
@Controller
public class DrinkListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkListController.class.getName());

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private AdultVerificationService adultVerificationService;


    @GetMapping("/list")
    public String listDrinks(Model model,
                             HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String pageNumberReq = req.getParameter("page");
        int currentPage;

        if (!userInputValidator.validatePageNumber(pageNumberReq)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(pageNumberReq);
        }

        final List<CategoryView> categories = categoryService.findAllCategories();

        Map<String, Object> dataModel = new HashMap<>();
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        adultVerificationService.verifyAge18(req, resp, contextHolder);
        adultVerificationService.setAdultFromCookies(req, contextHolder);

        if (contextHolder.getADULT() != null) {
            dataModel.put("adult", contextHolder.getADULT());
        }

        String email = contextHolder.getEmail();
        Map<String, String[]> searchParam = req.getParameterMap();
        SearchType searchType = drinkService.checkingSearchingCase(searchParam, currentPage);
        int maxPage = searchType.getMaxPage();

        List<FullDrinkView> drinkViewList = searchType.getDrinkViewList();
        String queryName = searchType.getQueryName();

        if (email != null && !email.isEmpty()) {

            List<FullDrinkView> favouritesList = userService.favouritesList(email);

            if (!favouritesList.isEmpty()) {
                List<Object> favouritesListModel = favouritesList.stream()
                        .map(FullDrinkView::getId)
                        .map(aLong -> Integer.parseInt(aLong.toString()))
                        .collect(Collectors.toList());

                dataModel.put("favourites", favouritesListModel);
            }
        }

        String servletPath = req.getServletPath();

        dataModel.put("servletPath", servletPath);
        dataModel.put("categories", categories);
        dataModel.put("maxPageSize", maxPage);
        dataModel.put("queryName", queryName);
        dataModel.put("drinkList", drinkViewList);
        dataModel.put("currentPage", currentPage);

        model.addAllAttributes(dataModel);
        return "recipeList";
    }


    @PostMapping(
            path = "/list"
    )
    public void addToFavourites(HttpServletRequest req) {

        String drinkId = req.getParameter("drinkId");

        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();
        if (email != null && !email.isEmpty()) {
            userService.updateUserFavouriteDrinks(email, drinkId);
            LOGGER.info("Added {}, to favourites of user: {}", drinkId, email);
        }
    }
}
