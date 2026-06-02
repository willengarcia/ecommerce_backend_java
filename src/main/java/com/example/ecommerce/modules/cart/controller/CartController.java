package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.dto.CartResponseDTO;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.service.CartService;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemResponseDTO>> getItemsByCart(
            @PathVariable Integer cartId
    ) {
        List<CartItemResponseDTO> response = cartService.getItemsByCart(cartId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAllCart(){
        List<Cart> cart = cartService.getAllCart();
        List<CartResponseDTO> response = cart.stream().map(c -> new CartResponseDTO(c.getId(), c.isStatus(), c.getDataCriacao(), c.getDataAtualizacao(), c.getUsuario().getId())).toList();
        return ResponseEntity.ok(response);
    }
}
