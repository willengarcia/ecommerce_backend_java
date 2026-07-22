package com.example.ecommerce.modules.checkout.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckoutRequestDTO(
        @NotNull(message = "O ID do Carrinho é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer cartId,
        @NotNull(message = "O ID do Endereço é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer addressId
) {
}
