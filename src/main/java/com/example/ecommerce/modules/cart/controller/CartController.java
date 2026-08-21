package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartDetailsResponse;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.dto.CartResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping
    public ResponseEntity<CartResponseDTO> create(@Valid @RequestBody CartCreateDTO dto) {
        Cart cart = cartService.createCart(dto.customerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(CartMapper.conversorCartResponseDTO(cart));
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemResponseDTO>> getItemsByCart(
            @Positive(message = "O ID do Cart tem que ser maior que 0")
            @PathVariable Integer cartId
    ) {
        List<CartItemResponseDTO> response = cartService.getItemsByCart(cartId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idCart}")
    public ResponseEntity<CartResponseDTO> deleteCart(@Positive(message = "O ID do Cart tem que ser maior que 0") @PathVariable Integer idCart) {
        cartService.deleteCart(idCart);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idCart}/customer/{idCustomer}")
    public ResponseEntity<CartDetailsResponse> getAllDetailsCart(@Positive(message = "O ID do Cart tem que ser maior que 0") @PathVariable Integer idCart, @Positive(message = "O ID do Customer tem que ser maior que 0") @PathVariable Integer idCustomer) {
        return ResponseEntity.ok(cartService.getDetailsByCart(idCart, idCustomer));
    }
}
