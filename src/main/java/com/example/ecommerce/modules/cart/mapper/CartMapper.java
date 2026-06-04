package com.example.ecommerce.modules.cart.mapper;

import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;

public class CartMapper {
    public ProductDTO conversorProductDTO(CartItem cartItem) {
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
        return productDTO;
    }
}
