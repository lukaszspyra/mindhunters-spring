package com.spyrka.mindhunters.services;

import com.spyrka.mindhunters.models.Category;
import com.spyrka.mindhunters.models.dto.CategoryView;
import com.spyrka.mindhunters.repositories.CategoryRepository;
import com.spyrka.mindhunters.services.mappers.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryView> findAllCategories() {
        LOGGER.debug("Return all categories names");
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toView(categories);
    }

    public Category getOrCreate(String name) {
        LOGGER.debug("Get or create new category");
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category();
            category.setName(name);
            category = categoryRepository.save(category);
        }
        return category;
    }
}
