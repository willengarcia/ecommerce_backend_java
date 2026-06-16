package com.example.ecommerce.modules.order.mapper;

import com.example.ecommerce.modules.order.dto.OrderItemResponseDTO;
import com.example.ecommerce.modules.order.dto.OrderResponseDTO;
import com.example.ecommerce.modules.order.model.Order;

import java.util.stream.Stream;

public class OrderMapper {
    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        Stream<OrderItemResponseDTO> orderItem = order.getOrderItem().stream().map(orderItems -> new OrderItemResponseDTO(orderItems.getNomeProduto(),
                orderItems.getSkuProduto(),
                orderItems.getQuantidade(),
                orderItems.getPrecoUnitario(),
                orderItems.getSubTotal(),
                orderItems.getProduct().getId()));
        return new OrderResponseDTO(order.getOrderId(), order.getValorTotal(),
                order.getStatus(), order.getUsuario().getId(), order.getAddress().getId(), orderItem);
    }
}
