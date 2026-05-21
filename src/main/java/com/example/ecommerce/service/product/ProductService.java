package com.example.ecommerce.service.product;

import com.example.ecommerce.dto.product.ProductCreateDTO;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.repository.category.RepositoryCategory;
import com.example.ecommerce.repository.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                        product.getCategoria().getId()
                )
        ).collect(Collectors.toList());
    }

    public Product criarProduct(ProductCreateDTO productDTO) {

        Product product = new Product();

        Long categoryId = productDTO.categoria_id();



        if (productDTO.nome().isEmpty() || productDTO.preco() == 0 || productDTO.estoqueMinimo() < 1 || categoryId == null){
            throw new RuntimeException("É necessário informar o Nome, preço, estoque mínimo maior que 1 e o ID da Categoria");
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

        product.setCategoria(category);

        return productRepository.save(product);
    }

    public Product buscarUmProduto(Long productId){
        Product produto = productRepository.findById(productId).orElseThrow();
        return produto;
    }

    public Product atualizarInformacoesPrincipais(Long produtoId, ProductCreateDTO produtos){
        Product produto = productRepository.findById(produtoId).orElseThrow();

        produto.setNome(produtos.nome());
        produto.setPreco(produtos.preco());
        produto.setQuantidadeEstoque(produtos.quantidadeEstoque());
        produto.setStatus(produtos.status());

        return productRepository.save(produto);
    }

    public ProductCreateDTO deletarUmProduto(Long produtoId){
        Product produto = productRepository.findById(produtoId).orElseThrow();
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
                produto.getCategoria().getId()
        );
        productRepository.delete(produto);
        return dto;
    }

    public Page<ProductCreateDTO> findAllPaginado(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(produto -> new ProductCreateDTO(
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
                        produto.getCategoria().getId()
                )
        );
    }

    public List<ProductCreateDTO> listarProdutosPorCategoria(Long categoriaId){
        List<ProductCreateDTO> produto = productRepository.findByCategoriaId(categoriaId);
        return produto;
    }

}
