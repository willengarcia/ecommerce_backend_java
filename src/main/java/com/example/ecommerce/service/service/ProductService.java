package com.example.ecommerce.service.service;

import com.example.ecommerce.dto.product.ProductCreateDTO;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.repository.category.RepositoryCategory;
import com.example.ecommerce.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final RepositoryCategory categoryRepository;


    public ProductService(ProductRepository productRepository, RepositoryCategory categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(
                product -> new ProductDTO(
                        product.getId(),
                        product.getNome(),
                        product.getSlug(),
                        product.getDescricao_curta(),
                        product.getDescricao(),
                        product.getPreco(),
                        product.getPreco_promocional(),
                        product.getQuantidade_estoque(),
                        product.getQuantidade_reservada(),
                        product.getEstoque_minimo(),
                        product.getSku(),
                        product.getPeso(),
                        product.getAltura(),
                        product.getLargura(),
                        product.getComprimento(),
                        product.getMedia_avaliacao(),
                        product.getTotal_avaliacoes(),
                        product.getStatus(),
                        product.getData_criacao(),
                        product.getCategoria().getId()
                )
        ).collect(Collectors.toList());
    }

    public Product criarProduct(ProductCreateDTO productDTO) {

        Product product = new Product();

        Long categoryId = productDTO.categoria_id();



        if (productDTO.nome().isEmpty() || productDTO.preco() == 0 || productDTO.estoque_minimo() < 1 || categoryId == null){
            throw new RuntimeException("É necessário informar o Nome, preço, estoque mínimo maior que 1 e o ID da Categoria");
        }

        product.setNome(productDTO.nome());
        product.setSlug(productDTO.slug());
        product.setDescricao_curta(productDTO.descricao_curta());
        product.setDescricao(productDTO.descricao());
        product.setPreco(productDTO.preco());
        product.setPreco_promocional(productDTO.preco_promocional());
        product.setQuantidade_estoque(productDTO.quantidade_estoque());
        product.setEstoque_minimo(productDTO.estoque_minimo());
        product.setSku(productDTO.sku());
        product.setPeso(productDTO.peso());
        product.setAltura(productDTO.altura());
        product.setLargura(productDTO.largura());
        product.setComprimento(productDTO.comprimento());
        product.setStatus(productDTO.status());

        Category category = categoryRepository.findById(categoryId).orElseThrow();

        product.setCategoria(category);

        return productRepository.save(product);
    }

    public Product buscarUmProduto(Long productId){
        Product produto = productRepository.findById(productId).orElseThrow();
        return produto;
    }

    public Product atualizarInformacoesPrincipais(Long produtoId, Product produtos){
        Product produto = productRepository.findById(produtoId).orElseThrow();

        produto.setNome(produtos.getNome());
        produto.setPreco(produtos.getPreco());
        produto.setQuantidade_estoque(produtos.getQuantidade_estoque());
        produto.setStatus(produtos.getStatus());

        productRepository.save(produto);

        return produto;
    }

}
