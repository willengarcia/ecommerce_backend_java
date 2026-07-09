package com.example.ecommerce.modules.order.controller;

import com.example.ecommerce.modules.checkout.dto.CheckoutRequestDTO;
import com.example.ecommerce.modules.checkout.service.CheckoutService;
import com.example.ecommerce.modules.order.dto.OrderResponseDTO;
import com.example.ecommerce.modules.order.dto.OrderUpdateAddressDTO;
import com.example.ecommerce.modules.order.mapper.OrderMapper;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{idCustomer}")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CheckoutRequestDTO orderCreateDTO, @PathVariable Integer idCustomer) {
        Order order = checkoutService.finalizarCompra(orderCreateDTO,  idCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toOrderResponseDTO(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrder() {
        List<Order> orders = orderService.getOrderById();
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (Order order : orders){
            orderResponseDTOList.add(OrderMapper.toOrderResponseDTO(order));
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOList);
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable Long idUser) {
        List<Order> orders = orderService.getOrdersByUserId(idUser);
        List<OrderResponseDTO> dto =  new ArrayList<>();
        for (Order order : orders) {
            dto.add(OrderMapper.toOrderResponseDTO(order));
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/{idOrder}/address")
    public ResponseEntity<OrderResponseDTO> updateOrderAddress(@PathVariable Long idOrder, @RequestBody OrderUpdateAddressDTO orderUpdateAddressDTO) {
        Order order = orderService.updateOrderAddress(idOrder, orderUpdateAddressDTO);
        return ResponseEntity.status(HttpStatus.OK).body(OrderMapper.toOrderResponseDTO(order));
    }
}
