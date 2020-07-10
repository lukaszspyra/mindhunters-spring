package com.spyrka.mindhunters.domain;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NamedQueries({
        @NamedQuery(
                name = "User.findAll",
                query = "SELECT u FROM User u"
        ),
        @NamedQuery(
                name = "User.findFavouritesList",
                query = "SELECT u.drinks FROM User u WHERE u.email like :email"
        ),
        @NamedQuery(
                name = "User.countFindFavouritesList",
                query = "SELECT COUNT (ud) FROM User u JOIN u.drinks ud WHERE u.email like :email"
        ),
        @NamedQuery(
                name = "User.findByEmail",
                query = "SELECT u FROM User u WHERE u.email LIKE:email"
        )

})

@Transactional
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "favourite",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "drink_id", referencedColumnName = "id"))
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}