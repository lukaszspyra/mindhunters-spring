package com.spyrka.mindhunters.services;

import com.infoshareacademy.domain.Drink;
import com.infoshareacademy.domain.Ingredient;
import com.infoshareacademy.domain.dto.DrinkLiveSearchView;
import com.infoshareacademy.domain.dto.IngredientView;
import com.infoshareacademy.repository.DrinkRepository;
import com.infoshareacademy.service.mapper.LiveSearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DrinkRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.infoshareacademy.service.DrinkRestService.class.getName());

    @Inject
    private DrinkRepository drinkRepository;

    @EJB
    private LiveSearchMapper liveSearchMapper;

    public List<DrinkLiveSearchView> findByNameLiveSearch(String partialDrinkName) {
        LOGGER.debug("Searching by name in livesearch");
        final List<Drink> drinks = drinkRepository.liveSearchDrinksByName(partialDrinkName);

        return liveSearchMapper.toView(drinks);
    }

    public List<IngredientView> findIngredientsLiveSearch(String partialIngredientName) {
        LOGGER.debug("Searching by ingredients in livesearch");
        final List<Ingredient> ingredients = drinkRepository.liveSearchIngredientsByName(partialIngredientName);

        return liveSearchMapper.ingredientsToView(ingredients);
    }

}
