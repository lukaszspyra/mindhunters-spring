package com.spyrka.mindhunters.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spyrka.mindhunters.jsonsupport.JsonCategoryDeserializer;

import java.util.Objects;

@JsonDeserialize(using = JsonCategoryDeserializer.class)
public class CategoryJson {

    @JsonProperty("strCategory")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "categoryName='" + categoryName + '\'' +
                "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
      CategoryJson categoryJson = (CategoryJson) o;
        return Objects.equals(categoryName, categoryJson.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }
}

