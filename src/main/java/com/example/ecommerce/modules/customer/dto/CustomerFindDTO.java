package com.example.ecommerce.modules.customer.dto;

import jakarta.validation.constraints.Size;

public record CustomerFindDTO(

        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nomeCompleto
) {
}
