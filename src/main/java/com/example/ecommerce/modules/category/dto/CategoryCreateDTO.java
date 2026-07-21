package com.example.ecommerce.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CategoryCreateDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 40, message = "O nome da categoria deve ter no máximo 40 caracteres")
        String name,
        @NotNull
        String description,
        @NotNull(message = "O status da categoria é obrigatório")
        Boolean ativo
) {
}
