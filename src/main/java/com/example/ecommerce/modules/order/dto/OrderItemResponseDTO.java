package com.example.ecommerce.modules.order.dto;

public record OrderItemResponseDTO(
        String nomeProduto,
        String skuProduto,
        Integer quantidade,
        Float precoUnitario,
        Float subTotal,
        Long productId
) {
}
