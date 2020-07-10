package com.spyrka.mindhunters.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(
                name = "Drink.getByDrinkId",
                query = "SELECT d FROM DrinkIngredient d where d.drinkId= :drinkId"
        ),

        @NamedQuery(
                name = "Drink.getByIngredientId",
                query = "SELECT dI FROM DrinkIngredient dI where dI.ingredient= :ingredinetId"
        ),

        @NamedQuery(
                name = "Drink.getByMeasureId",
                query = "SELECT mI FROM DrinkIngredient mI where mI.measure= :measureId"
        ),
        @NamedQuery(
                name = "Drink.deleteIngredientByDrink",
                query = "DELETE FROM DrinkIngredient di where di.drinkId.id = :drinkId"
        )
})
@Entity
@Table(name = "drink_ingredient")
public class DrinkIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_id")
    @Valid
    @NotNull
    private Measure measure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    @Valid
    @NotNull
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    @Valid
    @NotNull
    private Drink drinkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Drink getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(Drink drinkId) {
        this.drinkId = drinkId;
    }
}