package com.example.ecommerce.modules.cart.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemCreateDTO(
        @NotNull(message = "O ID do Carrinho é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer cartId,
        @NotNull(message = "O ID do Produto é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer productId
) {
}