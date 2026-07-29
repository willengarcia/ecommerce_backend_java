package com.example.ecommerce.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CategoryFindDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        String name
) {
}
