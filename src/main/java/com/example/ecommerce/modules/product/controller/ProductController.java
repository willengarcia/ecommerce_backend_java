package com.example.ecommerce.modules.product.controller;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductDTO;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody ProductCreateDTO productCreateDTO){
        try{
            Product produto = productService.criarProduct(productCreateDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(productCreateDTO);
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> buscarTodosProdutos() {
        try {
            List<ProductDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ProductCreateDTO buscarUmProduto(@PathVariable Integer productId){
        Product produto = productService.buscarUmProduto(productId);
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
        return dto;
    }

    @PutMapping("/{productId}")
    public ProductCreateDTO alterarInformacoesEssenciaisProduto(@PathVariable Integer productId, @RequestBody ProductCreateDTO produto){
        Product produtos = productService.atualizarInformacoesPrincipais(productId, produto);
        ProductCreateDTO dto = new ProductCreateDTO(
                produtos.getNome(),
                produtos.getSlug(),
                produtos.getDescricaoCurta(),
                produtos.getDescricao(),
                produtos.getPreco(),
                produtos.getPrecoPromocional(),
                produtos.getQuantidadeEstoque(),
                produtos.getEstoqueMinimo(),
                produtos.getSku(),
                produtos.getPeso(),
                produtos.getAltura(),
                produtos.getLargura(),
                produtos.getComprimento(),
                produtos.getStatus(),
                produtos.getCategoria().getId()
        );
        return dto;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer productId){
        ProductCreateDTO dto = productService.deletarUmProduto(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductCreateDTO>> listarProdutos(@PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<ProductCreateDTO> produtos = productService.findAllPaginado(pageable);

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductCreateDTO>> listarProdutosPorCategoria(@PathVariable Long id) {
        List<ProductCreateDTO> produtos = productService.listarProdutosPorCategoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProductCreateDTO>> listarProdutosPorNome(@PathVariable String nome) {
        List<ProductCreateDTO> dto = productService.buscarProdutoPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<ProductCreateDTO>> listarProdutosPorPreco() {
        List<ProductCreateDTO> dto = productService.buscarProdutoPorPrecoPorOrdem();
        return  ResponseEntity.status(HttpStatus.OK).body(dto);

    }
}
