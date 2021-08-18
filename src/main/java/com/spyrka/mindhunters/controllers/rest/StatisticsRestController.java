package com.spyrka.mindhunters.controllers.rest;

import com.spyrka.mindhunters.models.dto.StatisticsChartView;
import com.spyrka.mindhunters.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatisticsRestController {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/drinks/top-10")
    public ResponseEntity<List<StatisticsChartView>> topDrinks() {
        return ResponseEntity.ok(statisticsService.getTopDrinks());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<StatisticsChartView>> categoriesStats() {
        return ResponseEntity.ok(statisticsService.getCategoriesStats());
    }

    @GetMapping("/drinks/category")
    public ResponseEntity<List<StatisticsChartView>> getDrinksInAllCategories() {
        return ResponseEntity.ok(statisticsService.getDrinksInAllCategories());
    }

}
