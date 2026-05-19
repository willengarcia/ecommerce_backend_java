package com.example.ecommerce.controller.product;

import com.example.ecommerce.dto.product.ProductCreateDTO;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(ProductCreateDTO productCreateDTO){
        try{
            Product produto = productService.criarProduct(productCreateDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(produto);
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
    public Product buscarUmProduto(@PathVariable Long productId){
        Product produto = productService.buscarUmProduto(productId);
        return produto;
    }

    @PutMapping("/{productId}")
    public Product alterarInformacoesEssenciaisProduto(@PathVariable Long productId, @RequestBody ProductCreateDTO produto){
        Product produtos = productService.atualizarInformacoesPrincipais(productId, produto);
        return produtos;
    }
}
