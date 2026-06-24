package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartItemAddProductDTO;
import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.mapper.CartMapper;
import com.example.ecommerce.modules.cart.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartItem")
public class CartItemController extends CartMapper {
    private CartItemService cartItemService;

    public CartItemController( CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<?> createCartItem(@RequestBody CartItemCreateDTO dto) {
        try{
            CartItemResponseDTO cartItem = cartItemService.create(dto);
            return ResponseEntity.ok(cartItem);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/add/{idCart}")
    public ResponseEntity<?> addItemsToCartItem(@PathVariable Integer idCart, @RequestBody CartItemAddProductDTO dto) {
        CartItemResponseDTO cartItem = cartItemService.addItems(dto, idCart);

        try{
            if (cartItem != null) {
                return ResponseEntity.ok(cartItem);
            }else{
                return ResponseEntity.badRequest().body("Esse produto não está nesse Carrinho");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao tentar adicionar produto. "+e.getMessage());
        }
    }

    @DeleteMapping("/cart/{idCart}/item/{idCartItem}")
    public ResponseEntity<?>  removeItemFromCartItem(@PathVariable Integer idCart, @PathVariable Integer idCartItem) {
        cartItemService.deleteItem(idCart, idCartItem);
        return ResponseEntity.ok().build();
    }
}
