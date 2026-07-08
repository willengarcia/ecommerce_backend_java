package com.example.ecommerce.modules.cart.dto;

import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseResumeDTO;

public record CartItemResponseDTO(
        Long cartItemId,
        Integer quantidade,
        Float precoUnitario,
        Float subtotal,
        ProductResponseResumeDTO products
) {
}
