package com.example.ecommerce.modules.cart.mapper;

import com.example.ecommerce.modules.cart.dto.CartResponseDTO;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.model.Product;

public class CartMapper {
    public static ProductResponseDTO conversorProductDTO(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new ProductResponseDTO(
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
                product.getCategory().getId()
        );
    }
    public static CartResponseDTO conversorCartResponseDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO(
                cart.getId(),
                cart.isStatus(),
                cart.getValorTotal(),
                cart.getDataCriacao(),
                cart.getDataAtualizacao(),
                cart.getUsuario().getId()
        );
        return dto;
    }
}
