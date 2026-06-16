package com.example.ecommerce.modules.order.dto;

public record OrderItemCreateDTO(
        String nomeProduto,
        String skuProduto,
        Integer quantidade,
        Float precoUnitario,
        Float subTotal,
        Integer productId,
        Long orderId,
        Integer cartId
) {
}
