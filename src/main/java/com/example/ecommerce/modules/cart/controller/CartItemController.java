package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.service.CartItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/cartItem")
public class CartItemController extends CartMapper {
    private final CartItemService cartItemService;

    public CartItemController( CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<?> createCartItem(@Valid @RequestBody CartItemCreateDTO dto) {
        CartItemResponseDTO cartItem = cartItemService.create(dto);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/cart/{idCart}/item/{idCartItem}")
    public ResponseEntity<?>  removeItemFromCartItem(@Positive(message = "O ID do Cart tem que ser maior que 0") @PathVariable Integer idCart, @Positive(message = "O ID do CartItem tem que ser maior que 0") @PathVariable Integer idCartItem) {
        cartItemService.deleteItem(idCart, idCartItem);
        return ResponseEntity.ok().build();
    }
}
