package com.example.ecommerce.modules.order.service;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderEnum;
import com.example.ecommerce.modules.order.respository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService{
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderCreateDTO orderCreateDTO) {
        Order order = new Order();
        order.setStatus(OrderEnum.AGUARDANDO_PAGAMENTO);
        order.setUsuario(orderCreateDTO.cart().getUsuario());
        order.setAddress(orderCreateDTO.address());
        order.setValorTotal(orderCreateDTO.cart().getValorTotal());
        return orderRepository.save(order);
    }


    public List<Order> getOrderById() {
        List<Order> order = orderRepository.findAll();
        return order;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> order = orderRepository.findByUsuarioId(userId);
        return order.stream().toList();
    }

    public Order updateOrderAddress(Long orderId, Address address) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setAddress(address);
        return orderRepository.save(order);
    }
}
