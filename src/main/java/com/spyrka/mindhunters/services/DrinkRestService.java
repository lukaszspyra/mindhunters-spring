package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.Ingredient;
import com.spyrka.mindhunters.models.dto.DrinkLiveSearchView;
import com.spyrka.mindhunters.models.dto.IngredientView;
import com.spyrka.mindhunters.repositories.DrinkRepository;
import com.spyrka.mindhunters.repositories.IngredientRepository;
import com.spyrka.mindhunters.services.mappers.LiveSearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkRestService.class.getName());
    private static final Integer LIVE_SEARCH_LIMIT = 10;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private LiveSearchMapper liveSearchMapper;

    public List<DrinkLiveSearchView> findByNameLiveSearch(String partialDrinkName) {
        LOGGER.debug("Searching by name in livesearch");
        partialDrinkName = partialDrinkName + "%";
        Pageable pageable = PageRequest.of(0, LIVE_SEARCH_LIMIT);
        final List<Drink> drinks = drinkRepository.findDrinksByDrinkNameContaining(partialDrinkName, pageable);

        return liveSearchMapper.toView(drinks);
    }

    public List<IngredientView> findIngredientsLiveSearch(String partialIngredientName) {
        LOGGER.debug("Searching by ingredients in livesearch");

        Pageable pageable = PageRequest.of(0, LIVE_SEARCH_LIMIT);
        final List<Ingredient> ingredients = ingredientRepository.findByNameContaining(partialIngredientName, pageable);

        return liveSearchMapper.ingredientsToView(ingredients);
    }

}
