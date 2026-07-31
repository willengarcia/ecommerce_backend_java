package com.example.ecommerce.modules.product.mapper;

import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseResumeDTO;
import com.example.ecommerce.modules.product.model.Product;

public class ProductMapper {
    public static ProductResponseDTO toProductResponseDTO(Product product) {
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
                product.getStatus(),
                product.getDataCriacao(),
                product.getCategory().getId()
        );
    }

    public static ProductResponseResumeDTO toProductResponseResumeDTO(Product product) {
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

    public static Product toEntityProduct(ProductCreateDTO productDTO, Category category) {
        Product product = new Product(
            productDTO.nome(),
            productDTO.slug(),
            productDTO.descricaoCurta(),
            productDTO.descricao(),
            productDTO.preco(),
            productDTO.precoPromocional(),
            productDTO.quantidadeEstoque(),
            productDTO.estoqueMinimo(),
            productDTO.sku(),
            productDTO.peso(),
            productDTO.altura(),
            productDTO.largura(),
            productDTO.comprimento()
        );
        product.setCategory(category);
        return product;
    }
}
