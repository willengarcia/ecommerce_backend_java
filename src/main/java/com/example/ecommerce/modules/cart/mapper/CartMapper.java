package com.example.ecommerce.modules.cart.mapper;

import com.example.ecommerce.modules.cart.dto.CartResponseDTO;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.product.dto.ProductResponseResumeDTO;
import com.example.ecommerce.modules.product.model.Product;

public class CartMapper {
    public static ProductResponseResumeDTO conversorProductDTO(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new ProductResponseResumeDTO(
                product.getId(),
                product.getNome(),
                product.getSlug(),
                product.getDescricaoCurta(),
                product.getDescricao(),
                product.getPreco(),
                product.getSku()
        );
    }
    public static CartResponseDTO conversorCartResponseDTO(Cart cart) {
        return new CartResponseDTO(
                cart.getId(),
                cart.isStatus(),
                cart.getValorTotal(),
                cart.getDataCriacao(),
                cart.getDataAtualizacao(),
                cart.getUsuario().getId()
        );
    }
}
