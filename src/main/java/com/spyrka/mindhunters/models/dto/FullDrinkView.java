package com.spyrka.mindhunters.models.dto;

import java.util.ArrayList;
import java.util.List;


public class FullDrinkView {

    private Long id;

    private String drinkId;

    private String drinkName;

    private CategoryView categoryView;

    private String alcoholStatus;

    private String recipe;

    private List<DrinkIngredientView> drinkIngredientViews = new ArrayList<>();

    private List<UserView> users = new ArrayList<>();

    private List<StatisticsView> statisticsViews = new ArrayList<>();

    private Long parentId;

    private String manageAction;

    private String image;

    private String date;

    private boolean isApproved;

    private String confirmUserEmail;

    public String getManageAction() {
        return manageAction;
    }

    public void setManageAction(String manageAction) {
        this.manageAction = manageAction;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getConfirmUserEmail() {
        return confirmUserEmail;
    }

    public void setConfirmUserEmail(String confirmUserEmail) {
        this.confirmUserEmail = confirmUserEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public CategoryView getCategoryView() {
        return categoryView;
    }

    public void setCategoryView(CategoryView categoryView) {
        this.categoryView = categoryView;
    }

    public String getAlcoholStatus() {
        return alcoholStatus;
    }

    public void setAlcoholStatus(String alcoholStatus) {
        this.alcoholStatus = alcoholStatus;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public List<DrinkIngredientView> getDrinkIngredientViews() {
        return drinkIngredientViews;
    }

    public void setDrinkIngredientViews(List<DrinkIngredientView> drinkIngredientViews) {
        this.drinkIngredientViews = drinkIngredientViews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StatisticsView> getStatisticsViews() {
        return statisticsViews;
    }

    public void setStatisticsViews(List<StatisticsView> statisticsViews) {
        this.statisticsViews = statisticsViews;
    }

    public List<UserView> getUsers() {
        return users;
    }

    public void setUsers(List<UserView> users) {
        this.users = users;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Drink: {" +
                "id=" + id +
                ", drinkName='" + drinkName + '\'' +
                ", alcoholStatus='" + alcoholStatus + '\'' +
                ", last modification ='" + date + '\'' +
                '}';
    }

}
