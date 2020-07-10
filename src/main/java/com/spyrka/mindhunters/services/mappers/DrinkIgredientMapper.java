package com.spyrka.mindhunters.services.mappers;


public class DrinkIgredientMapper {

    /*@Inject
    private IngredientService ingredientService;

    @Inject
    private MeasureService measureService;


    public DrinkIngredientView toView(DrinkIngredient drinkIngredient) {
        DrinkIngredientView view = new DrinkIngredientView(
                drinkIngredient.getIngredient().getName(),
                drinkIngredient.getMeasure().getQuantity()
        );
        return view;
    }

    public List<DrinkIngredientView> toView(List<DrinkIngredient> drinkIngredients) {
        List<DrinkIngredientView> drinkIngredientViews = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : drinkIngredients) {
            drinkIngredientViews.add(toView(drinkIngredient));
        }
        return drinkIngredientViews;
    }

    public DrinkIngredient toEntity(DrinkIngredientView drinkIngredientView) {
        DrinkIngredient drinkIngredient = new DrinkIngredient();

        Ingredient ingredient = ingredientService.getOrCreate(drinkIngredientView.getName());
        Measure measure = measureService.getOrCreate(drinkIngredientView.getMeasure());

        drinkIngredient.setIngredient(ingredient);

        drinkIngredient.setMeasure(measure);

        return drinkIngredient;
    }*/
}
