package com.spyrka.mindhunters.models;


import javax.persistence.*;
import javax.validation.Valid;

@NamedQueries({
        @NamedQuery(
                name = "Statistics.getTopDrinks",
                query = "SELECT s.drink, COUNT(s.drink) as quantity FROM Statistics s GROUP BY s.drink ORDER BY " +
                        "quantity DESC"),

        @NamedQuery(
                name = "Statistics.getCategoriesStats",
                query = "SELECT c.name, COUNT(d.drinkName) as quantity FROM Statistics s JOIN s.drink d JOIN" +
                        " " +
                        "d.category c " +
                        "GROUP BY c.name ORDER BY quantity DESC")

})

@Entity
@Table(name = "statistics")
public class Statistics {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    @Valid
    private Drink drink;


    @Column(name = "time_stamp")
    private Long timeStamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
