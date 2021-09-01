package com.spyrka.mindhunters.controller;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.service.DrinkService;
import com.spyrka.mindhunters.service.RatingService;
import com.spyrka.mindhunters.service.StatisticsService;
import com.spyrka.mindhunters.service.UserService;
import com.spyrka.mindhunters.service.validator.UserInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SingleViewController {

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserInputValidator userInputValidator;

    @Autowired
    private UserService userService;


    @GetMapping("/single-view")
    public String doGet(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        final String idParam = req.getParameter("drink");
        Long drinkId = userInputValidator.stringToLongConverter(idParam);

        Map<String, Object> dataModel = new HashMap<>();

        String email = getCredentials(req, dataModel);

        if (drinkId < 0) {
            dataModel.put("errorMessage", "Wrong input.\n");
        } else {
            final FullDrinkView foundDrinkById = passDTOtoView(drinkId, dataModel);

            statisticsService.addToStatistics(foundDrinkById);

            dataModel.put("rating", ratingService.getCalculatedRatingByDrinkId(drinkId));
        }

        sentFavouritesToView(dataModel, email);

        model.addAllAttributes(dataModel);
        return "singleDrinkView";
    }

    private void sentFavouritesToView(Map<String, Object> dataModel, String email) {
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
    }

    private FullDrinkView passDTOtoView(Long drinkId, Map<String, Object> dataModel) {
        final FullDrinkView foundDrinkById = drinkService.getFullDrinkViewById(drinkId);


        if (foundDrinkById == null) {
            dataModel.put("errorMessage", "Drink not found.\n");
        }

        dataModel.put("drink", foundDrinkById);
        return foundDrinkById;
    }

    private String getCredentials(HttpServletRequest req, Map<String, Object> dataModel) {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        dataModel.put("name", contextHolder.getName());
        dataModel.put("role", contextHolder.getRole());

        return contextHolder.getEmail();
    }


}
