package com.example.ecommerce.modules.order.controller;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.checkout.dto.CheckoutRequestDTO;
import com.example.ecommerce.modules.checkout.service.CheckoutService;
import com.example.ecommerce.modules.order.dto.OrderResponseDTO;
import com.example.ecommerce.modules.order.mapper.OrderMapper;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CheckoutService checkoutService;
    public OrderController(OrderService orderService, CheckoutService checkoutService) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody CheckoutRequestDTO orderCreateDTO) {
        Order order = checkoutService.finalizarCompra(orderCreateDTO);
        return OrderMapper.toOrderResponseDTO(order);
    }

    @GetMapping
    public List<OrderResponseDTO> getOrder() {
        List<Order> orders = orderService.getOrderById();
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (Order order : orders){
            orderResponseDTOList.add(OrderMapper.toOrderResponseDTO(order));
        }
        return orderResponseDTOList;
    }

    @GetMapping("/user/{idUser}")
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable Long idUser) {
        List<Order> orders = orderService.getOrdersByUserId(idUser);
        List<OrderResponseDTO> dto =  new ArrayList<>();
        for (Order order : orders) {
            dto.add(OrderMapper.toOrderResponseDTO(order));
        }
        return dto;
    }

    @PutMapping("/{idOrder}/address")
    public OrderResponseDTO updateOrderAddress(@PathVariable Long idOrder, @RequestBody Address address) {
        Order order = orderService.updateOrderAddress(idOrder, address);
        return OrderMapper.toOrderResponseDTO(order);
    }
}
