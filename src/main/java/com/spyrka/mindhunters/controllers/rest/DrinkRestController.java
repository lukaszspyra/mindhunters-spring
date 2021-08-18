package com.spyrka.mindhunters.controllers.rest;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/drink-management")
public class DrinkRestController {

    @Autowired
    private DrinkService drinkService;

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<FullDrinkView> readDrink(@PathVariable("id") Long id) {
        FullDrinkView foundDrink = drinkService.getFullDrinkViewById(id);
        if (foundDrink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundDrink);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrink(@PathVariable("id") Long id) {
        if (drinkService.deleteDrinkById(id)) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable("id") Long id, Drink updateDrink, HttpServletRequest request) {
        ContextHolder contextHolder = new ContextHolder(request.getSession());
        String email = contextHolder.getEmail();
        updateDrink.setConfirmUserEmail(email);
        if (email != null && drinkService.addOrUpdate(id, updateDrink, contextHolder)) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }


    @PostMapping("")
    public ResponseEntity<Drink> createDrink(Drink updateDrink, HttpServletRequest request) {

        ContextHolder contextHolder = new ContextHolder(request.getSession());
        String email = contextHolder.getEmail();

        updateDrink.setConfirmUserEmail(email);

        if (email != null && drinkService.addOrUpdate(0L, updateDrink, contextHolder)) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}

