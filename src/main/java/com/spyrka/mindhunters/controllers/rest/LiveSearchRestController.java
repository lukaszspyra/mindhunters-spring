package com.spyrka.mindhunters.controllers.rest;


import com.spyrka.mindhunters.models.dto.DrinkLiveSearchView;
import com.spyrka.mindhunters.services.DrinkRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/search")
public class LiveSearchRestController {

    @Autowired
    private DrinkRestService drinkRestService;

    @GetMapping("/drink/{partialName}")
    public ResponseEntity<List<DrinkLiveSearchView>> drink(@PathVariable("partialName") String partialName) {
        return ResponseEntity.ok(drinkRestService.findByNameLiveSearch(partialName));
    }


}