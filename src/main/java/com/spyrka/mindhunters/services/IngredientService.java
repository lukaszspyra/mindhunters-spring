package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.Ingredient;
import com.spyrka.mindhunters.models.dto.IngredientView;
import com.spyrka.mindhunters.repositories.IngredientRepository;
import com.spyrka.mindhunters.services.mappers.IngredientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IngredientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientService.class.getName());

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    public List<IngredientView> findIngredientsByName(String partialIngredientName) {
        LOGGER.debug("Searching for ingredients by partial name input");

        final List<Ingredient> ingredientsByName = ingredientRepository.findByNameContaining(partialIngredientName);

        return ingredientMapper.toView(ingredientsByName);
    }

    public Ingredient getOrCreate(String name) {
        Ingredient ingredient = ingredientRepository.findByName(name);
        if (ingredient == null) {
            ingredient = new Ingredient();
            ingredient.setName(name);
            ingredientRepository.save(ingredient);
        }
        return ingredient;

    }

    public List<IngredientView> findIngredientsByName(List<String> partialIngredientNames) {
        LOGGER.debug("Searching for ingredients by partial name list input");
        Set<Ingredient> ingredientsByName = new HashSet<>();
        for (String name : partialIngredientNames) {
            ingredientsByName.addAll(ingredientRepository.findByNameContaining(name));
        }
        return ingredientMapper.toView(List.copyOf(ingredientsByName));
    }
}
