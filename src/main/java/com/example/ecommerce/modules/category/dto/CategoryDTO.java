package com.example.ecommerce.modules.category.dto;

import java.time.LocalDate;

public record CategoryDTO(
        Long id,
        String name,
        String description,
        Boolean ativo,
        LocalDate dataAtualizacao
) {
}
