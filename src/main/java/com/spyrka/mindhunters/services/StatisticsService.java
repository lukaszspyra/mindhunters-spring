package com.spyrka.mindhunters.services;


import com.spyrka.mindhunters.models.Drink;
import com.spyrka.mindhunters.models.Statistics;
import com.spyrka.mindhunters.models.dto.FullDrinkView;
import com.spyrka.mindhunters.models.dto.StatisticsChartView;
import com.spyrka.mindhunters.repositories.DrinkRepository;
import com.spyrka.mindhunters.repositories.StatisticsRepository;
import com.spyrka.mindhunters.services.mappers.LiveSearchMapper;
import com.spyrka.mindhunters.services.mappers.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {


    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private LiveSearchMapper liveSearchMapper;

    @Autowired
    private StatisticsMapper statisticsMapper;

    public void addToStatistics(FullDrinkView fullDrinkView) {
        Statistics statistics = statisticsMapper.toEntity(fullDrinkView);
        long timeStamp = System.currentTimeMillis();

        statistics.setTimeStamp(timeStamp);
        statisticsRepository.save(statistics);

    }

    public List<StatisticsChartView> getTopDrinks() {
        List statistics = statisticsRepository.getTopDrinks();
        Object[] row;
        List<StatisticsChartView> views = new ArrayList<>();

        for (Object o : statistics) {

            if (o instanceof Object[]) {

                row = (Object[]) o;

                if (row[0] instanceof Drink) {
                    Drink drink = ((Drink) row[0]);
                    StatisticsChartView view = new StatisticsChartView();
                    view.setName(drink.getDrinkName());
                    view.setQuantity((Long) row[1]);

                    views.add(view);

                }
            }
        }

        return views;
    }

    public List<StatisticsChartView> getCategoriesStats() {
        List statistics = statisticsRepository.getCategoriesStats();
        Object[] row;
        List<StatisticsChartView> views = new ArrayList<>();

        for (Object o : statistics) {

            if (o instanceof Object[]) {

                row = (Object[]) o;

                StatisticsChartView view = new StatisticsChartView();
                view.setName(String.valueOf(row[0]));
                view.setQuantity((Long) row[1]);

                views.add(view);

            }
        }
        return views;
    }


    public List<StatisticsChartView> getDrinksInAllCategories() {
        List statistics = drinkRepository.getDrinksInAllCategories();
        Object[] row;
        List<StatisticsChartView> views = new ArrayList<>();

        for (Object o : statistics) {

            if (o instanceof Object[]) {

                row = (Object[]) o;

                StatisticsChartView view = new StatisticsChartView();
                view.setName(String.valueOf(row[0]));
                view.setQuantity((Long) row[1]);

                views.add(view);
            }
        }


        return views;
    }


}
