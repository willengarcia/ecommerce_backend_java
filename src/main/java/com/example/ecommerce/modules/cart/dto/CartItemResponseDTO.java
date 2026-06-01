package com.example.ecommerce.modules.cart.dto;

import com.example.ecommerce.modules.product.dto.ProductDTO;

public record CartItemResponseDTO(
        Integer quantidade,
        Float precoUnitario,
        Float subtotal,
        ProductDTO products
) {
}
