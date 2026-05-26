package com.example.ecommerce.modules.category.repository;

import com.example.ecommerce.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCategory extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);


}
