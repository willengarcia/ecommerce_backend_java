package com.example.ecommerce.modules.product.service;

import com.example.ecommerce.modules.category.exception.CategoryNotFoundException;
import com.example.ecommerce.modules.category.exception.InactiveCategoryException;
import com.example.ecommerce.modules.importation.product.dto.ImportProductRowDTO;
import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.product.dto.ProductUpdateDTO;
import com.example.ecommerce.modules.product.exception.*;
import com.example.ecommerce.modules.product.mapper.ProductMapper;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.category.repository.RepositoryCategory;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RepositoryCategory categoryRepository;


    public ProductService(ProductRepository productRepository, RepositoryCategory categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    public Product createProduct(ProductCreateDTO productDTO) {
        List<Product> productConsulta = productRepository.findByNomeContainingIgnoreCase(productDTO.nome());
        Product p = productRepository.findBySkuContainingIgnoreCase(productDTO.sku());
        if (!productConsulta.isEmpty()) {
            throw new ProductAlreadyExistsException("Nome de produto já existe!");
        }
        if (p != null) {
            throw new DuplicateProductException("SKU do produto já existe!");
        }

        Long categoryId = productDTO.categoriaId();

        if (productDTO.nome() == null || productDTO.nome().isBlank()
                || productDTO.preco() == null || productDTO.preco().compareTo(BigDecimal.ZERO) <= 0
                || productDTO.estoqueMinimo() == null || productDTO.estoqueMinimo() < 1
                || categoryId == null || productDTO.quantidadeEstoque() <= 0) {

            throw new InvalidProductDataException(
                    "É necessário informar o Nome, preço, estoque mínimo maior que 0, quantidade estoque maior que 0, e o ID da Categoria"
            );
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("Categoria não existe!")
        );

        if (!category.isAtivo()){
            throw new InactiveCategoryException("Não é possível vincular uma categoria Inativa a um Produto");
        }

        return productRepository.save(ProductMapper.toEntityProduct(productDTO, category));
    }

    public ProductResponseDTO findOneProduct(Integer productId){
        Product produto = productRepository.findById(productId).orElseThrow();
        return ProductMapper.toProductResponseDTO(produto);
    }

    @Transactional
    public void deleteOneProduct(Integer produtoId){
        Product produto = productRepository.findById(produtoId).orElseThrow(
                () -> new ProductNotFoundException("Produto não encontrado!")
        );
        productRepository.delete(produto);
        ProductMapper.toProductResponseDTO(produto);
    }

    public Page<ProductResponseDTO> findAllPaginado(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toProductResponseDTO
        );
    }

    public List<ProductResponseDTO> listProductsByCategory(Long categoriaId){
        List<Product> produto = productRepository.findByCategory_Id(categoriaId).stream().toList();
        return produto.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> searchProductByName(String nome){
        List<Product> product = productRepository.findByNomeContainingIgnoreCase(nome);
        return product.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> searchProductByPriceAndOrder(){
        List<Product> products = productRepository.findAllByOrderByPrecoAsc();
        return products.stream()
                .map(ProductMapper::toProductResponseDTO)
                .toList();
    }

    @Transactional
    public ProductResponseDTO updateProductData(Integer produtoId, ProductUpdateDTO produtos){
        Product produto = productRepository.findById(produtoId).orElseThrow(
                () -> new ProductNotFoundException("Produto não encontrado!")
        );
        if (produtos.nome() != null && !produtos.nome().isBlank()){
            List<Product> productConsulta = productRepository.findByNomeContainingIgnoreCase(produtos.nome());
            if (!productConsulta.isEmpty()) {
                throw new ProductAlreadyExistsException("Nome de produto já existe!");
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
                    () -> new CategoryNotFoundException("Categoria não existe!")
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
            throw new DuplicateProductException(
                    "Já existe um produto com o SKU: " + dto.sku()
            );
        }

        if (productRepository.existsBySlug(dto.slug())) {
            throw new DuplicateProductException(
                    "Já existe um produto com o slug: " + dto.slug()
            );
        }

        Category category = categoryRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoryNotFoundException(
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
