package com.example.ecommerce.modules.product.mapper;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.model.Product;

public class ProductMapper {
    public static ProductResponseDTO toProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(
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
                product.getStatus(),
                product.getDataCriacao(),
                product.getCategory().getId()
        );
        return productResponseDTO;
    }
    public static ProductCreateDTO toProductCreateDTO(Product produto) {
        ProductCreateDTO dto = new ProductCreateDTO(
                produto.getNome(),
                produto.getSlug(),
                produto.getDescricaoCurta(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getPrecoPromocional(),
                produto.getQuantidadeEstoque(),
                produto.getEstoqueMinimo(),
                produto.getSku(),
                produto.getPeso(),
                produto.getAltura(),
                produto.getLargura(),
                produto.getComprimento(),
                produto.getStatus(),
                produto.getCategory().getId()
        );
        return dto;
    }
}
