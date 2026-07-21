package com.example.ecommerce.modules.category.dto;

import java.time.LocalDate;

public record CategoryListDTO(
        Long categoryId,
        String name,
        String description,
        Boolean ativo,
        LocalDate dataAtualizacao
) {
}
