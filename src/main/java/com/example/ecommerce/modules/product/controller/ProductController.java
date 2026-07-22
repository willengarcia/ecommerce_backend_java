package com.example.ecommerce.modules.product.controller;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductImageResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductUpdateDTO;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.service.ProductImageService;
import com.example.ecommerce.modules.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;


    public ProductController(ProductService productService, ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(@Valid @RequestBody ProductCreateDTO productCreateDTO){
        try{
            Product produto = productService.criarProduct(productCreateDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(produto);
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductImageResponseDTO> upload(@PathVariable Long productId, @RequestPart("file") MultipartFile file, @RequestParam(name = "imagemPrincipal", defaultValue = "false") Boolean imagemPrincipal
    ) {
        ProductImageResponseDTO response =
                productImageService.upload(
                        productId,
                        file,
                        imagemPrincipal
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<List<ProductImageResponseDTO>> listar(@PathVariable Integer productId){
        return ResponseEntity.ok(
                productImageService.listarPorProduto(productId)
        );
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
        return productService.buscarUmProduto(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deletarProduto(@PathVariable Integer productId){
        ProductResponseDTO dto = productService.deletarUmProduto(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> remover(@PathVariable Long imageId) {
        productImageService.remover(imageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponseDTO>> listarProdutos(@PageableDefault(sort = "id") Pageable pageable) {

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
    public ResponseEntity<ProductResponseDTO> updateDataProduct(@PathVariable Integer idProduct, @Valid @RequestBody ProductUpdateDTO productUpdateDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.alterarDadosProdutos(idProduct, productUpdateDTO));
    }
}
