package com.spyrka.mindhunters.services;


import com.infoshareacademy.domain.Drink;
import com.infoshareacademy.domain.Statistics;
import com.infoshareacademy.domain.dto.FullDrinkView;
import com.infoshareacademy.domain.dto.StatisticsChartView;
import com.infoshareacademy.repository.StatisticsRepositoryBean;
import com.infoshareacademy.service.mapper.LiveSearchMapper;
import com.infoshareacademy.service.mapper.StatisticsMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class StatisticsService {


    @EJB
    private StatisticsRepositoryBean statisticsRepositoryBean;

    @EJB
    private LiveSearchMapper liveSearchMapper;

    @Inject
    private StatisticsMapper statisticsMapper;

    public void addToStatistics(FullDrinkView fullDrinkView) {
        Statistics statistics = statisticsMapper.toEntity(fullDrinkView);
        long timeStamp = System.currentTimeMillis();

        statistics.setTimeStamp(timeStamp);
        statisticsRepositoryBean.addToStatistics(statistics);

    }

    public List<StatisticsChartView> getTopDrinks() {
        List statistics = statisticsRepositoryBean.getTopDrinks();
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
        List statistics = statisticsRepositoryBean.getCategoriesStats();
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
        List statistics = statisticsRepositoryBean.getDrinksInAllCategories();
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
