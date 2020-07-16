package com.spyrka.mindhunters.services;

import com.spyrka.mindhunters.models.Category;
import com.spyrka.mindhunters.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

/*    @Inject
    private CategoryMapper categoryMapper;

    public List<CategoryView> findAllCategories() {
        LOGGER.debug("Return all categories names");
        List<Category> categories = categoryRepositoryBean.findAllNames();
        return categoryMapper.toView(categories);
    }*/

    public Category getOrCreate(String name) {
        Category category = categoryRepository.getByName(name);
        if (category == null) {
            category = new Category();
            category.setName(name);
            categoryRepository.save(category);
        }
        return category;
    }
}
