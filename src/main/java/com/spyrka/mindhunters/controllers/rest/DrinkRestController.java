package com.spyrka.mindhunters.controllers.rest;


import com.spyrka.mindhunters.context.ContextHolder;
import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.services.AdminManagementRecipeService;
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

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<FullDrinkView> readDrink(@PathVariable("id") Long id) {
        FullDrinkView foundDrink = drinkService.getFullDrinkViewById(id);
        if (foundDrink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundDrink);
    }

    /**
     * Handles user proposals to delete drink from database
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

//
//
//    @POST
//    @Path("")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response add(Drink updateDrink) {
//
//        ContextHolder contextHolder = new ContextHolder(request.getSession());
//        String email = contextHolder.getEmail();
//
//        updateDrink.setConfirmUserEmail(email);
//
//        if (email != null && drinkService.addOrUpdate(0L, updateDrink,contextHolder)) {
//            return Response.status(204).build();
//        } else {
//            return Response.status(404).build();
//        }
//    }


    //tu idzie forma po edycji i naciniseciu submit bez approvala

    @PostMapping("/{id}")
    public ResponseEntity<String> updateDrink(@PathVariable("id") Long id, Drink submittedDrinkUpdate, HttpServletRequest req) {
        ContextHolder contextHolder = new ContextHolder(req.getSession());
        String email = contextHolder.getEmail();
        submittedDrinkUpdate.setConfirmUserEmail(email);
        if (email != null && adminManagementRecipeService.addOrUpdateDrink(id, submittedDrinkUpdate)) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
