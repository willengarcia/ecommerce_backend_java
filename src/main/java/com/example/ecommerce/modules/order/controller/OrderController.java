package com.example.ecommerce.modules.order.controller;

import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        Order order = orderService.createOrder(orderCreateDTO);
        return new OrderResponseDTO(order.getOrderId(), order.getValorTotal(), order.getStatus());
    }
}
