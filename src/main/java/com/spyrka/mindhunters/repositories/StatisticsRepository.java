package com.spyrka.mindhunters.repositories;

import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.Statistics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {


    List getTopDrinks(Pageable pageable);

    List getCategoriesStats();

    void deleteStatisticsByDrink(@Valid Drink drink);

    default List getTop10Drinks() {
        return getTopDrinks(PageRequest.of(0, 10));
    }

    //TODO metoda do statystyk przeniesiona do drinkRepository

}
