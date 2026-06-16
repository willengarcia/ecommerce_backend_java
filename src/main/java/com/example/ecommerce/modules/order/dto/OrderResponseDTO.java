package com.example.ecommerce.modules.order.dto;

import com.example.ecommerce.modules.order.model.OrderEnum;

public record OrderResponseDTO(
        Long orderId,
        Float valorTotal,
        OrderEnum status,
        Integer usuarioId,
        Integer addressId,
        java.util.stream.Stream<OrderItemResponseDTO> orderItemResponseDTO
) {
}
