package com.example.ecommerce.modules.cart.dto;


public record CartItemCreateDTO(
        Integer cartId,
        Integer productId
) {
}