package com.example.ecommerce.modules.category.repository;

import com.example.ecommerce.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositoryCategory extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
    @Query("""
        SELECT COUNT(p) > 0
        FROM Product p
        WHERE p.category.id = :categoryId
    """)
    boolean hasProducts(@Param("categoryId") Long categoryId);
}
