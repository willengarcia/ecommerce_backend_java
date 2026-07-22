package com.example.ecommerce.modules.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartCreateDTO(
        @NotNull(message = "O ID do Usuário é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer customerId
) {
}