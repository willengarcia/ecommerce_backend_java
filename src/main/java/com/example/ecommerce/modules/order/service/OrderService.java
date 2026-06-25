package com.example.ecommerce.modules.order.service;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.order.dto.OrderCreateDTO;
import com.example.ecommerce.modules.order.dto.OrderUpdateAddressDTO;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.model.OrderEnum;
import com.example.ecommerce.modules.order.respository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService{
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    public OrderService(OrderRepository orderRepository,  AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
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
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> order = orderRepository.findByUsuarioId(userId);
        return order.stream().toList();
    }

    public Order updateOrderAddress(Long orderId, OrderUpdateAddressDTO addressId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Address address = addressRepository.getById(addressId.addressId());
        if (!Objects.equals(order.getUsuario().getId(), address.getUsuario().getId())) {
            throw new RuntimeException("Endereço não pertence ao usuário do carrinho");
        }
        order.setAddress(address);
        return orderRepository.save(order);
    }
}
