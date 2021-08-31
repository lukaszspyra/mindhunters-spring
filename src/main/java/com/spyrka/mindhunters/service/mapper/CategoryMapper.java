package com.spyrka.mindhunters.service.mapper;

import com.spyrka.mindhunters.model.Category;
import com.spyrka.mindhunters.model.dto.CategoryView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryMapper {


    public CategoryView toView(Category category) {
        CategoryView categoryView = new CategoryView();
        categoryView.setId(category.getId());
        categoryView.setName(category.getName());
        return categoryView;
    }

    public List<CategoryView> toView(List<Category> categories) {
        List<CategoryView> categoryViews = new ArrayList<>();
        for (Category category : categories) {
            categoryViews.add(toView(category));
        }
        return categoryViews;
    }

    public Category toEntity(CategoryView categoryView) {
        Category category = new Category();
        category.setId(categoryView.getId());
        category.setName(categoryView.getName());
        return category;
    }


}
