package com.spyrka.mindhunters.services;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueryParamBuilder {

    public String buildIngrQuery(List<String> ingredientNamesFiltered) {
        StringBuilder queryIngrBuilder = new StringBuilder();
        queryIngrBuilder.append("search=ingr&ing=");
        queryIngrBuilder.append(ingredientNamesFiltered
                .stream()
                .collect(Collectors.joining("&ing=")));
        return queryIngrBuilder.toString();
    }

    public String buildNameQuery(String partialDrinkName) {
        StringBuilder queryNameBuilder = new StringBuilder();
        queryNameBuilder.append("search=name&");
        return queryNameBuilder.append("name=" + partialDrinkName).toString();
    }
}
