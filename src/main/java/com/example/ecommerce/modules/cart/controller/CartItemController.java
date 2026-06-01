package com.example.ecommerce.modules.cart.controller;

import com.example.ecommerce.modules.cart.dto.CartItemCreateDTO;
import com.example.ecommerce.modules.cart.dto.CartItemResponseDTO;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.cart.service.CartItemService;
import com.example.ecommerce.modules.cart.service.CartService;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
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
        Product product = cartItem.getProduct();

        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getNome(),
                product.getSlug(),
                product.getDescricaoCurta(),
                product.getDescricao(),
                product.getPreco(),
                product.getPrecoPromocional(),
                product.getQuantidadeEstoque(),
                product.getQuantidadeReservada(),
                product.getEstoqueMinimo(),
                product.getSku(),
                product.getPeso(),
                product.getAltura(),
                product.getLargura(),
                product.getComprimento(),
                product.getMediaAvaliacao(),
                product.getTotalAvaliacoes(),
                product.isStatus(),
                product.getDataCriacao(),
                product.getCategoria().getId()
        );
        CartItemResponseDTO dtoResponse = new CartItemResponseDTO(
                cartItem.getQuantidade(),
                cartItem.getPrecoUnitario(),
                cartItem.getSubtotal(),
                productDTO
        );
        return dtoResponse;
    }
}
