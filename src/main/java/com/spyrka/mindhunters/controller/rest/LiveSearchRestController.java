package com.spyrka.mindhunters.controller.rest;


import com.spyrka.mindhunters.model.dto.DrinkLiveSearchView;
import com.spyrka.mindhunters.model.dto.IngredientView;
import com.spyrka.mindhunters.service.DrinkRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class LiveSearchRestController {

    @Autowired
    private DrinkRestService drinkRestService;

    @GetMapping("/drink/{partialName}")
    public ResponseEntity<List<DrinkLiveSearchView>> drink(@PathVariable("partialName") String partialName) {
        return ResponseEntity.ok(drinkRestService.findByNameLiveSearch(partialName));
    }

    @GetMapping("/ingredient/{partialName}")
    public ResponseEntity<List<IngredientView>> ingredient(@PathVariable("partialName") String partialName) {
        return ResponseEntity.ok(drinkRestService.findIngredientsLiveSearch(partialName));
    }
}