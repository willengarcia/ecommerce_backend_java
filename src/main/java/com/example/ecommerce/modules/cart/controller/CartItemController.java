package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartItemAddProductDTO;
import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.service.CartItemService;
import com.example.ecommerce.modules.cart.service.CartService;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartItem")
public class CartItemController extends CartMapper {
    private CartItemService cartItemService;
    private CartService cartService;
    private ProductService productService;

    public CartItemController( CartItemService cartItemService, CartService cartService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @PostMapping
    public CartItemResponseDTO createCartItem(@RequestBody CartItemCreateDTO dto) {
        CartItem cartItem = cartItemService.create(dto);

        CartItemResponseDTO dtoResponse = new CartItemResponseDTO(
                cartItem.getId(),
                cartItem.getQuantidade(),
                cartItem.getPrecoUnitario(),
                cartItem.getSubtotal(),
                conversorProductDTO(cartItem)
        );
        return dtoResponse;
    }

    @PutMapping("/add/{idCart}")
    public ResponseEntity<?> addItemsToCartItem(@PathVariable Integer idCart, @RequestBody CartItemAddProductDTO dto) {
        CartItemResponseDTO cartItem = cartItemService.addItems(dto, idCart);

        if (cartItem != null) {
            return ResponseEntity.ok(cartItem);
        }else{
            return ResponseEntity.badRequest().body("Esse produto não está nesse Carrinho");
        }
    }
}
