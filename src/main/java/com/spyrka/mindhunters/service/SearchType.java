package com.spyrka.mindhunters.service;


import com.spyrka.mindhunters.model.dto.FullDrinkView;

import java.util.List;

public class SearchType {

    private List<FullDrinkView> drinkViewList;

    private int maxPage;

    private String queryName;

    public List<FullDrinkView> getDrinkViewList() {
        return drinkViewList;
    }

    public void setDrinkViewList(List<FullDrinkView> drinkViewList) {
        this.drinkViewList = drinkViewList;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
}
