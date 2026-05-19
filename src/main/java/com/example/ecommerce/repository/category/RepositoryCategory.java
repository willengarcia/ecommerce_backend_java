package com.example.ecommerce.repository.category;

import com.example.ecommerce.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCategory extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);


}
