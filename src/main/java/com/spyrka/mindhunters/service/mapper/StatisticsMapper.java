package com.spyrka.mindhunters.service.mapper;


import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.Statistics;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.model.dto.StatisticsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FullDrinkMapper fullDrinkMapper;

    public Statistics toEntity(FullDrinkView fullDrinkView) {
        Drink drink = new Drink();
        drink.setId(fullDrinkView.getId());
        drink.setDrinkId(fullDrinkView.getDrinkId());
        drink.setDrinkName(fullDrinkView.getDrinkName());
        drink.setCategory(categoryMapper.toEntity(fullDrinkView.getCategoryView()));

        Statistics statistics = new Statistics();
        statistics.setDrink(drink);
        return statistics;
    }

    public StatisticsView toView(Statistics statistics) {

        StatisticsView statisticsView = new StatisticsView();
        statisticsView.setId(statistics.getId());
        statisticsView.setFullDrinkView(fullDrinkMapper.toView(statistics.getDrink()));
        statisticsView.setTimeStamp(statistics.getTimeStamp());

        return statisticsView;
    }


    public List<StatisticsView> toView(List<Statistics> statisticsList) {

        List<StatisticsView> statisticsViews = new ArrayList<>();

        for (Statistics statistics : statisticsList) {
            statisticsViews.add(toView(statistics));
        }

        return statisticsViews;
    }
}
