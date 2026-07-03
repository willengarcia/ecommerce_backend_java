package com.example.ecommerce.modules.product.controller;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductUpdateDTO;
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
            List<ProductResponseDTO> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ProductResponseDTO buscarUmProduto(@PathVariable Integer productId){
        ProductResponseDTO produto = productService.buscarUmProduto(productId);
        return produto;
    }

    @PutMapping("/{productId}")
    public ProductResponseDTO alterarInformacoesEssenciaisProduto(@PathVariable Integer productId, @RequestBody ProductCreateDTO produto){
        ProductResponseDTO produtos = productService.atualizarInformacoesPrincipais(productId, produto);
        return produtos;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer productId){
        ProductResponseDTO dto = productService.deletarUmProduto(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponseDTO>> listarProdutos(@PageableDefault(size = 10, sort = "id") Pageable pageable) {

        Page<ProductResponseDTO> produtos = productService.findAllPaginado(pageable);

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponseDTO>> listarProdutosPorCategoria(@PathVariable Long id) {
        List<ProductResponseDTO> produtos = productService.listarProdutosPorCategoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProductResponseDTO>> listarProdutosPorNome(@PathVariable String nome) {
        List<ProductResponseDTO> dto = productService.buscarProdutoPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<ProductResponseDTO>> listarProdutosPorPreco() {
        List<ProductResponseDTO> dto = productService.buscarProdutoPorPrecoPorOrdem();
        return  ResponseEntity.status(HttpStatus.OK).body(dto);

    }

    @PatchMapping("/{idProduct}")
    public ResponseEntity<ProductResponseDTO> updateDataProduct(@PathVariable Integer idProduct, @RequestBody ProductUpdateDTO productUpdateDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.alterarDadosProdutos(idProduct, productUpdateDTO));
    }
}
