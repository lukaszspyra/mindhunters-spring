package com.spyrka.mindhunters.repository;

import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.Statistics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {


    List getTopDrinks(Pageable pageable);

    List getCategoriesStats();

    void deleteStatisticsByDrink(@Valid Drink drink);

}
