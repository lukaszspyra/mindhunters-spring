package com.spyrka.mindhunters.controller.rest;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.service.AdminManagementRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Used to receive {@link com.spyrka.mindhunters.model.User} requests to delete or update drink recipes
 */
@RestController
@RequestMapping("/api/drink-management")
public class DrinkRestController {

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    /**
     * Receives user proposals to delete drink from database
     *
     * @param id  of the drink proposed to be deleted
     * @param req in order to get user from session
     * @return 204 if change successful or 404 if required drink not in database
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrink(@PathVariable("id") Long id, HttpServletRequest req) {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();
        if (adminManagementRecipeService.proposeDeleteDrink(id, email)) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    /**
     * Receives drink update from user view editDrinkForm, format json is automatically converted by Jackson
     *
     * @param id             of drink currently edited
     * @param submittedDrink with no ID
     * @param req            containing {@link com.spyrka.mindhunters.model.User} email
     */
    @PostMapping("/{id}")
    public ResponseEntity<String> updateDrink(@PathVariable("id") Long id, @RequestBody Drink submittedDrink, HttpServletRequest req) {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();
        submittedDrink.setConfirmUserEmail(email);
        if (email != null && adminManagementRecipeService.updateDrink(id, submittedDrink)) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
