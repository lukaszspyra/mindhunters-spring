package com.spyrka.mindhunters.service;

import com.spyrka.mindhunters.model.Drink;
import com.spyrka.mindhunters.model.Ingredient;
import com.spyrka.mindhunters.model.dto.FullDrinkView;
import com.spyrka.mindhunters.model.dto.IngredientView;
import com.spyrka.mindhunters.repository.DrinkRepository;
import com.spyrka.mindhunters.service.mapper.FullDrinkMapper;
import com.spyrka.mindhunters.service.mapper.IngredientMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrinkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrinkService.class.getName());
    private static final Integer PAGE_SIZE = 20;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private FullDrinkMapper fullDrinkMapper;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private AdminManagementRecipeService adminManagementRecipeService;

    @Transactional
    public FullDrinkView getFullDrinkViewById(Long drinkId) {
        LOGGER.debug("Searching drink id");
        Drink foundDrink = drinkRepository.findById(drinkId).orElse(null);
        if (foundDrink == null) {
            return null;
        }
        return fullDrinkMapper.toView(foundDrink);
    }

    public Drink getDrinkById(Long drinkId) {
        LOGGER.debug("Searching drink id");
        Drink foundDrink = drinkRepository.findById(drinkId).orElse(null);
        return foundDrink;
    }

    public List<FullDrinkView> findDrinksByName(String partialDrinkName, int pageNumber) {
        LOGGER.debug("Searching drinks by name with pagination");

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("drinkName"));

        List<Drink> foundDrinks = drinkRepository.findDrinksByDrinkNameContaining(partialDrinkName, pageable);
        return fullDrinkMapper.toView(foundDrinks);
    }

    public List<FullDrinkView> findByIngredients(List<IngredientView> ingredientViews, int pageNumber) {
        LOGGER.debug("Searching drinks by ingredients with pagination");

        final List<Ingredient> ingredients = ingredientMapper.toEntity(ingredientViews);

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE);

        final List<Drink> foundDrinksByIngredients = drinkRepository.findByIngredients(ingredients, pageable);
        return fullDrinkMapper.toView(foundDrinksByIngredients);
    }

    public List<FullDrinkView> findAllDrinks(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("drinkName"));

        List<Drink> drinks = drinkRepository.findAllDrinks(pageable);
        return fullDrinkMapper.toView(drinks);
    }

    public List<FullDrinkView> findByCategories(List<Long> category, int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("drinkName"));

        List<Drink> drinks = drinkRepository.findByCategories(category, pageable);
        return fullDrinkMapper.toView(drinks);
    }

    public List<FullDrinkView> findByAlcoholStatus(List<String> alcoholStatus, int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("drinkName"));

        List<Drink> drinks = drinkRepository.findByAlcoholStatus(alcoholStatus, pageable);
        return fullDrinkMapper.toView(drinks);
    }

    public List<FullDrinkView> findByCategoriesAndAlcoholStatus(List<Long> category, List<String> alcoholStatus, int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("drinkName"));

        List<Drink> drinks = drinkRepository.findByCategoriesAndAlcoholStatus(category, alcoholStatus, pageable);
        return fullDrinkMapper.toView(drinks);
    }

    public SearchType checkingSearchingCase(Map<String, String[]> searchParam, int currentPage) {

        SearchType searchType = new SearchType();

        List<String> alcoholStatusQuery = Optional.ofNullable((searchParam.get("alcoholStatus")))
                .map(Arrays::asList).orElse(Collections.emptyList());
        List<String> categoriesQuery = Optional.ofNullable((searchParam.get("category")))
                .map(Arrays::asList).orElse(Collections.emptyList());

        List<Long> searchingCategory = new ArrayList<>();
        List<String> searchingAlcoholStatus = new ArrayList<>();

        String queryName;

        if (searchParam.containsKey("category") && searchParam.containsKey("alcoholStatus")) {

            searchingCategory = searchByCategoryService(searchParam.get("category"));

            searchingAlcoholStatus = searchByAlcoholStatusService(searchParam.get("alcoholStatus"));

        } else if (searchParam.containsKey("category")) {

            searchingCategory = searchByCategoryService(searchParam.get("category"));

        } else if (searchParam.containsKey("alcoholStatus")) {

            searchingAlcoholStatus = searchByAlcoholStatusService(searchParam.get("alcoholStatus"));

        }


        if (searchingCategory.size() > 0 && searchingAlcoholStatus.size() > 0) {


            List<FullDrinkView> drinksByCategoriesAndAlcoholStatus = findByCategoriesAndAlcoholStatus(searchingCategory, searchingAlcoholStatus, currentPage);

            queryName = "category=" + String.join("&&category=", categoriesQuery)
                    + "&&alcoholStatus=" + String.join("&&alcoholStatus=", alcoholStatusQuery);


            searchType.setDrinkViewList(drinksByCategoriesAndAlcoholStatus);

            searchType.setQueryName(queryName);

            int maxPage = countPagesByCategoriesAndAlcoholStatus(searchingCategory, searchingAlcoholStatus);

            searchType.setMaxPage(maxPage);


        } else if (searchingCategory.size() > 0) {


            List<FullDrinkView> drinksByCategories = findByCategories(searchingCategory, currentPage);

            searchType.setDrinkViewList(drinksByCategories);

            queryName = "category=" + String.join("&&category=", categoriesQuery);

            searchType.setQueryName(queryName);

            int maxPage = countPagesByCategories(searchingCategory);

            searchType.setMaxPage(maxPage);


        } else if (searchingAlcoholStatus.size() > 0) {

            List<FullDrinkView> drinksByAlcoholStatus = findByAlcoholStatus(searchingAlcoholStatus, currentPage);

            searchType.setDrinkViewList(drinksByAlcoholStatus);


            queryName = "&&alcoholStatus=" + String.join("&&alcoholStatus=", alcoholStatusQuery);

            searchType.setQueryName(queryName);

            int maxPage = countPagesByAlcoholStatus(searchingAlcoholStatus);

            searchType.setMaxPage(maxPage);


        } else {

            List<FullDrinkView> paginatedDrinkList = findAllDrinks(currentPage);

            int maxPage = countPagesFindAll();

            searchType.setDrinkViewList(paginatedDrinkList);
            searchType.setMaxPage(maxPage);

            return searchType;

        }

        return searchType;

    }

    private List<String> searchByAlcoholStatusService(String[] query) {
        return Arrays.stream(query)
                .filter(Objects::nonNull)
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toList());
    }

    private List<Long> searchByCategoryService(String[] query) {
        return Arrays.stream(query)
                .filter(Objects::nonNull)
                .filter(StringUtils::isNoneBlank)
                .filter(s -> s.matches("[0-9]+"))
                .map(s -> Long.valueOf(s))
                .collect(Collectors.toList());
    }

    public int countPagesByAlcoholStatus(List<String> alcoholStatus) {
        int querySize = drinkRepository.countPagesByAlcoholStatus(alcoholStatus);

        return getMaxPageNumber(querySize);

    }

    public int countPagesByCategoriesAndAlcoholStatus(List<Long> category, List<String> alcoholStatus) {
        int querySize = drinkRepository.countPagesByCategoriesAndAlcoholStatus(category, alcoholStatus);

        return getMaxPageNumber(querySize);
    }


    public int countPagesByCategories(List<Long> category) {
        int querySize = drinkRepository.countPagesByCategories(category);

        return getMaxPageNumber(querySize);
    }


    public int countPagesFindAll() {
        int querySize = drinkRepository.countPagesFindAll();

        return getMaxPageNumber(querySize);
    }


    public List<FullDrinkView> findDrinksToApprove() {

        List<Drink> drinks = drinkRepository.findDrinksToApprove();
        return fullDrinkMapper.toView(drinks);
    }


    public List<FullDrinkView> findDeletedDrinksToApprove() {

        List<Drink> drinks = drinkRepository.findOldDrinksToApprove("DELETE");
        return fullDrinkMapper.toView(drinks);
    }


    public List<FullDrinkView> findEditedDrinksToApprove() {

        List<Drink> drinks = drinkRepository.findOldDrinksToApprove("EDIT");
        return fullDrinkMapper.toView(drinks);
    }

    public List<FullDrinkView> findNewDrinksToApprove() {

        List<Drink> drinks = drinkRepository.findNewDrinksToApprove(List.of("EDIT", "DELETE"));
        return fullDrinkMapper.toView(drinks);
    }

    private int getMaxPageNumber(int querySize) {
        return (int) Math.ceil((Double.valueOf(querySize) / PAGE_SIZE));
    }


    public void save(Drink drink) {
        drinkRepository.save(drink);
    }


    public int countPagesByIngredients(List<IngredientView> ingredientViews) {
        final List<Ingredient> ingredients = ingredientMapper.toEntity(ingredientViews);

        int querySize = drinkRepository.countPagesByIngredients(ingredients);
        return getMaxPageNumber(querySize);
    }
}
