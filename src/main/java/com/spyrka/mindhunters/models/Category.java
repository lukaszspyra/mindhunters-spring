package com.spyrka.mindhunters.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "Category.findAll",
                query = "SELECT c FROM Category c"
        ),
        @NamedQuery(
                name = "Category.findAllNames",
                query = "SELECT c.name FROM Category c"
        ),
        @NamedQuery(
                name = "Category.getByName",
                query = "SELECT c FROM Category c where c.name= :name"
        )
})


@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL})
    private List<Drink> drinks = new ArrayList<>();



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

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }
}
