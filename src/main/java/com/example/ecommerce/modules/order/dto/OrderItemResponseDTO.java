package com.example.ecommerce.modules.order.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        String nomeProduto,
        String skuProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subTotal,
        Long productId
) {
}
