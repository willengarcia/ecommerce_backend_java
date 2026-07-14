package com.example.ecommerce.modules.product.service;

import com.example.ecommerce.modules.importation.product.dto.ImportProductRowDTO;
import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.product.dto.ProductUpdateDTO;
import com.example.ecommerce.modules.product.exception.ProductException;
import com.example.ecommerce.modules.product.mapper.ProductMapper;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.category.repository.RepositoryCategory;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Product criarProduct(ProductCreateDTO productDTO) {
        List<Product> productConsulta = productRepository.findByNomeContainingIgnoreCase(productDTO.nome());
        Product p = productRepository.findBySkuContainingIgnoreCase(productDTO.sku());
        if (!productConsulta.isEmpty()) {
            throw new ProductException("Nome de produto já existe!");
        }
        if (p != null) {
            throw new ProductException("SKU do produto já existe!");
        }

        Long categoryId = productDTO.categoriaId();

        if (productDTO.nome() == null || productDTO.nome().isBlank()
                || productDTO.preco() == null || productDTO.preco() <= 0
                || productDTO.estoqueMinimo() == null || productDTO.estoqueMinimo() < 1
                || categoryId == null || productDTO.quantidadeEstoque() <= 0) {

            throw new RuntimeException(
                    "É necessário informar o Nome, preço, estoque mínimo maior que 0, quantidade estoque maior que 0, e o ID da Categoria"
            );
        }
        Product product = new Product();


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

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ProductException("Categoria não existe!")
        );

        if (!category.isAtivo()){
            throw new ProductException("Não é possível vincular uma categoria Inativa a um Produto");
        }

        product.setCategory(category);

        return productRepository.save(product);
    }

    public List<ProductResponseDTO> findAll() {

        return productRepository.findAll().stream().map(
                ProductMapper::toProductResponseDTO
        ).collect(Collectors.toList());
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
        Product produto = productRepository.findById(produtoId).orElseThrow(
                () -> new ProductException("Produto não encontrado!")
        );
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

    public ProductResponseDTO alterarDadosProdutos(Integer produtoId, ProductUpdateDTO produtos){
        Product produto = productRepository.findById(produtoId).orElseThrow(
                () -> new ProductException("Produto não encontrado!")
        );
        if (produtos.nome() != null && !produtos.nome().isBlank()){
            List<Product> productConsulta = productRepository.findByNomeContainingIgnoreCase(produtos.nome());
            if (!productConsulta.isEmpty()) {
                throw new ProductException("Nome de produto já existe!");
            }
            produto.setNome(produtos.nome());
        }
        if (produtos.slug() != null && !produtos.slug().isBlank()){
            produto.setSlug(produtos.slug());
        }
        if (produtos.descricaoCurta() != null && !produtos.descricaoCurta().isBlank()){
            produto.setDescricaoCurta(produtos.descricaoCurta());
        }
        if (produtos.descricao() != null && !produtos.descricao().isBlank()){
            produto.setDescricao(produtos.descricao());
        }
        if (produtos.categoriaId() != null){
            Category category = categoryRepository.findById(produtos.categoriaId()).orElseThrow(
                    () -> new ProductException("Categoria não existe!")
            );
            produto.setCategory(category);
        }
        if (produtos.preco() != null){
            produto.setPreco(produtos.preco());
        }
        if (produtos.precoPromocional() != null){
            produto.setPrecoPromocional(produtos.precoPromocional());
        }
        if (produtos.estoqueMinimo() != null){
            produto.setEstoqueMinimo(produtos.estoqueMinimo());
        }
        if (produtos.peso() != null){
            produto.setPeso(produtos.peso());
        }
        if (produtos.altura() != null){
            produto.setAltura(produtos.altura());
        }
        if (produtos.largura() != null){
            produto.setLargura(produtos.largura());
        }
        if (produtos.comprimento() != null){
            produto.setComprimento(produtos.comprimento());
        }
        if (produtos.status() != null){
            produto.setStatus(produtos.status());
        }
        productRepository.save(produto);
        return ProductMapper.toProductResponseDTO(produto);
    }

    @Transactional
    public void createFromImport(ImportProductRowDTO dto) {
        if (productRepository.existsBySku(dto.sku())) {
            throw new IllegalArgumentException(
                    "Já existe um produto com o SKU: " + dto.sku()
            );
        }

        if (productRepository.existsBySlug(dto.slug())) {
            throw new IllegalArgumentException(
                    "Já existe um produto com o slug: " + dto.slug()
            );
        }

        Category category = categoryRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Categoria não encontrada: " + dto.categoriaId()
                ));

        Product product = new Product();

        product.setNome(dto.nome());
        product.setSlug(dto.slug());
        product.setDescricaoCurta(dto.descricaoCurta());
        product.setDescricao(dto.descricao());
        product.setPreco(dto.preco());
        product.setPrecoPromocional(dto.precoPromocional());
        product.setQuantidadeEstoque(dto.quantidadeEstoque());
        product.setEstoqueMinimo(dto.estoqueMinimo());
        product.setSku(dto.sku());
        product.setPeso(dto.peso());
        product.setAltura(dto.altura());
        product.setLargura(dto.largura());
        product.setComprimento(dto.comprimento());
        product.setStatus(dto.status());
        product.setCategory(category);

        productRepository.save(product);
    }
}
