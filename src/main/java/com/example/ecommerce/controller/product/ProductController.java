package com.example.ecommerce.controller.product;

import com.example.ecommerce.dto.product.ProductCreateDTO;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.model.product.Product;
import com.example.ecommerce.service.product.ProductService;
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
    public ProductCreateDTO buscarUmProduto(@PathVariable Long productId){
        Product produto = productService.buscarUmProduto(productId);
        ProductCreateDTO dto = new ProductCreateDTO(
                produto.getNome(),
                produto.getSlug(),
                produto.getDescricao_curta(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getPreco_promocional(),
                produto.getQuantidade_estoque(),
                produto.getQuantidade_reservada(),
                produto.getSku(),
                produto.getPeso(),
                produto.getAltura(),
                produto.getLargura(),
                produto.getComprimento(),
                produto.isStatus(),
                produto.getCategoria().getId()
        );
        return dto;
    }

    @PutMapping("/{productId}")
    public ProductCreateDTO alterarInformacoesEssenciaisProduto(@PathVariable Long productId, @RequestBody ProductCreateDTO produto){
        Product produtos = productService.atualizarInformacoesPrincipais(productId, produto);
        ProductCreateDTO dto = new ProductCreateDTO(
                produtos.getNome(),
                produtos.getSlug(),
                produtos.getDescricao_curta(),
                produtos.getDescricao(),
                produtos.getPreco(),
                produtos.getPreco_promocional(),
                produtos.getQuantidade_estoque(),
                produtos.getQuantidade_reservada(),
                produtos.getSku(),
                produtos.getPeso(),
                produtos.getAltura(),
                produtos.getLargura(),
                produtos.getComprimento(),
                produtos.isStatus(),
                produtos.getCategoria().getId()
        );
        return dto;
    }
}
