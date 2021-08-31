package com.spyrka.mindhunters.service.mapper;

import com.spyrka.mindhunters.model.Category;
import com.spyrka.mindhunters.model.json.CategoryJson;
import com.spyrka.mindhunters.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadCategoryMapper {

    @Autowired
    private CategoryService categoryService;

    public Category toEntity(CategoryJson categoryJson) {
        Category category = categoryService.getOrCreate(categoryJson.getCategoryName());
        return category;
    }
}
