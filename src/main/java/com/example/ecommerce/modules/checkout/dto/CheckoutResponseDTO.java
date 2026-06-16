package com.example.ecommerce.modules.checkout.dto;

public record CheckoutResponseDTO(
        Long cartId,
        Integer addressId
) {
}
