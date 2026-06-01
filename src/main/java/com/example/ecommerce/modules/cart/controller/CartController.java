package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartResponseDTO;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping
    public ResponseEntity<CartResponseDTO> create(@RequestBody CartCreateDTO dto) {
        Cart cart = cartService.createCart(dto.customerId());

        CartResponseDTO response = new CartResponseDTO(
                cart.getId(),
                cart.isStatus(),
                cart.getDataCriacao(),
                cart.getDataAtualizacao(),
                cart.getUsuario().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
