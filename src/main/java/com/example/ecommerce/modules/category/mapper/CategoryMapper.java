package com.example.ecommerce.modules.category.mapper;


import com.example.ecommerce.modules.category.dto.CategoryCreateDTO;
import com.example.ecommerce.modules.category.dto.CategoryListDTO;
import com.example.ecommerce.modules.category.model.Category;

import java.time.LocalDate;

public class CategoryMapper {
    public static Category toCategoryCreateDTO(CategoryCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setAtivo(dto.ativo());
        category.setDataCriacao(LocalDate.now());
        category.setDataAtualizacao(LocalDate.now());


        return category;
    }
    public static CategoryCreateDTO toCategoryCreateDTO(Category dto) {
        return new CategoryCreateDTO(
                dto.getName(),
                dto.getDescription(),
                dto.isAtivo()
        );
    }
    public static CategoryListDTO toCategoryListDTO(Category dto) {
        return new CategoryListDTO(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.isAtivo(),
                dto.getDataAtualizacao()
        );
    }
}
