package com.example.ecommerce.modules.checkout.dto;

public record CheckoutRequestDTO(
        Integer cartId,
        Integer addressId
) {
}
