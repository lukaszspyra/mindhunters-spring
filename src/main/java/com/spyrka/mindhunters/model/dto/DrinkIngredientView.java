package com.spyrka.mindhunters.model.dto;

public class DrinkIngredientView {

    private String name;

    private String measure;

    public DrinkIngredientView(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }


}
