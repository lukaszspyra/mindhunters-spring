package com.spyrka.mindhunters.services.mappers;

import com.spyrka.mindhunters.models.Category;
import com.spyrka.mindhunters.models.json.CategoryJson;
import com.spyrka.mindhunters.services.CategoryService;
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
