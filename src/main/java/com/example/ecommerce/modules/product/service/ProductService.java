package com.example.ecommerce.modules.product.service;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.product.mapper.ProductMapper;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.category.repository.RepositoryCategory;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
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

    public List<ProductResponseDTO> findAll() {

        return productRepository.findAll().stream().map(
                ProductMapper::toProductResponseDTO
        ).collect(Collectors.toList());
    }

    public Product criarProduct(ProductCreateDTO productDTO) {

        Product product = new Product();

        Long categoryId = productDTO.categoriaId();



        if (productDTO.nome() == null || productDTO.nome().isBlank()
                || productDTO.preco() == null || productDTO.preco() <= 0
                || productDTO.estoqueMinimo() == null || productDTO.estoqueMinimo() < 1
                || categoryId == null) {

            throw new RuntimeException(
                    "É necessário informar o Nome, preço, estoque mínimo maior que 1 e o ID da Categoria"
            );
        }

        product.setNome(productDTO.nome());
        product.setSlug(productDTO.slug());
        product.setDescricaoCurta(productDTO.descricaoCurta());
        product.setDescricao(productDTO.descricao());
        product.setPreco(productDTO.preco());
        product.setPrecoPromocional(productDTO.precoPromocional());
        product.setQuantidadeEstoque(productDTO.quantidadeEstoque());
        product.setQuantidadeReservada(0);
        product.setDataCriacao(LocalDate.now());
        product.setTotalAvaliacoes(0);
        product.setEstoqueMinimo(productDTO.estoqueMinimo());
        product.setSku(productDTO.sku());
        product.setPeso(productDTO.peso());
        product.setAltura(productDTO.altura());
        product.setLargura(productDTO.largura());
        product.setComprimento(productDTO.comprimento());
        product.setStatus(productDTO.status());

        Category category = categoryRepository.findById(categoryId).orElseThrow();

        product.setCategory(category);

        return productRepository.save(product);
    }

    public ProductResponseDTO buscarUmProduto(Integer productId){
        Product produto = productRepository.findById(productId).orElseThrow();
        return ProductMapper.toProductResponseDTO(produto);
    }

    public ProductResponseDTO atualizarInformacoesPrincipais(Integer produtoId, ProductCreateDTO produtos){
        Product produto = productRepository.findById(produtoId).orElseThrow();

        produto.setNome(produtos.nome());
        produto.setPreco(produtos.preco());
        produto.setQuantidadeEstoque(produtos.quantidadeEstoque());
        produto.setStatus(produtos.status());
        productRepository.save(produto);
        return ProductMapper.toProductResponseDTO(produto);
    }

    public ProductResponseDTO deletarUmProduto(Integer produtoId){
        Product produto = productRepository.findById(produtoId).orElseThrow();
        productRepository.delete(produto);
        return ProductMapper.toProductResponseDTO(produto);
    }

    public Page<ProductResponseDTO> findAllPaginado(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toProductResponseDTO
        );
    }

    public List<ProductResponseDTO> listarProdutosPorCategoria(Long categoriaId){
        List<Product> produto = productRepository.findByCategory_Id(categoriaId).stream().toList();
        return produto.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> buscarProdutoPorNome(String nome){
        List<Product> product = productRepository.findByNomeContainingIgnoreCase(nome);
        return product.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> buscarProdutoPorPrecoPorOrdem(){
        List<Product> products = productRepository.findAllByOrderByPrecoAsc();
        return products.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }
}
