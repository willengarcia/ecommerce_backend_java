package com.example.ecommerce.dto.category;

import java.time.LocalDate;

public record CategoryDTO(
        Long id,
        String name,
        String description,
        Boolean ativo,
        LocalDate dataAtualizacao
) {
}
