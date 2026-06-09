package com.example.ecommerce.modules.order.service;

import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.respository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderCreateDTO orderCreateDTO) {
        Order order = new Order();
        order.setValorTotal(orderCreateDTO.valorTotal());
        order.setStatus(orderCreateDTO.status());
        order.setUsuario(orderCreateDTO.usuarioId());
        order.setAddress(orderCreateDTO.addressId());
        return orderRepository.save(order);
    }
}
