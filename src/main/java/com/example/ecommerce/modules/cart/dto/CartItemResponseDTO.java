package com.example.ecommerce.modules.cart.dto;

import com.example.ecommerce.modules.product.dto.ProductResponseResumeDTO;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long cartItemId,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        ProductResponseResumeDTO products
) {
}
