package com.spyrka.mindhunters.repository;

import com.spyrka.mindhunters.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}
