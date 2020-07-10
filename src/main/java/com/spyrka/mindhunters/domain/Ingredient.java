package com.spyrka.mindhunters.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(
                name = "Ingredient.getByName",
                query = "SELECT i FROM Ingredient i where i.name= :name"
        ),
        @NamedQuery(name = "Ingredient.findIngredientsByPartialName",
                query = "SELECT DISTINCT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER" +
                        "(:partialIngredientName)")
})

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
