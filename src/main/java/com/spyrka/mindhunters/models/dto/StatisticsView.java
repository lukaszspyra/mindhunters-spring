package com.spyrka.mindhunters.models.dto;


public class StatisticsView {

    private Long id;

    private FullDrinkView fullDrinkView;

    private Long timeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FullDrinkView getFullDrinkView() {
        return fullDrinkView;
    }

    public void setFullDrinkView(FullDrinkView fullDrinkView) {
        this.fullDrinkView = fullDrinkView;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
