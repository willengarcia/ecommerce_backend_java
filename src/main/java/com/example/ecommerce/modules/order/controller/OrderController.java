package com.example.ecommerce.modules.order.controller;

import com.example.ecommerce.modules.checkout.dto.CheckoutRequestDTO;
import com.example.ecommerce.modules.checkout.service.CheckoutService;
import com.example.ecommerce.modules.order.dto.OrderResponseDTO;
import com.example.ecommerce.modules.order.dto.OrderUpdateAddressDTO;
import com.example.ecommerce.modules.order.mapper.OrderMapper;
import com.example.ecommerce.modules.order.model.Order;
import com.example.ecommerce.modules.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CheckoutService checkoutService;
    public OrderController(OrderService orderService, CheckoutService checkoutService) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
    }

    @PostMapping("/{idCustomer}")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody CheckoutRequestDTO orderCreateDTO, @Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idCustomer) {
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

    @GetMapping("/user/{idCustomer}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Long idCustomer) {
        List<Order> orders = orderService.getOrdersByUserId(idCustomer);
        List<OrderResponseDTO> dto =  new ArrayList<>();
        for (Order order : orders) {
            dto.add(OrderMapper.toOrderResponseDTO(order));
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping("/{idOrder}/address")
    public ResponseEntity<OrderResponseDTO> updateOrderAddress(@Positive(message = "O ID do Order tem que ser maior que 0") @PathVariable Long idOrder, @Valid @RequestBody OrderUpdateAddressDTO orderUpdateAddressDTO) {
        Order order = orderService.updateOrderAddress(idOrder, orderUpdateAddressDTO);
        return ResponseEntity.status(HttpStatus.OK).body(OrderMapper.toOrderResponseDTO(order));
    }
}
