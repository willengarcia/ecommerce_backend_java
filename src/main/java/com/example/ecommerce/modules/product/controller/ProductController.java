package com.example.ecommerce.modules.product.controller;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.dto.ProductImageResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductResponseDTO;
import com.example.ecommerce.modules.product.dto.ProductUpdateDTO;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.service.ProductImageService;
import com.example.ecommerce.modules.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;


    public ProductController(ProductService productService, ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO){
        try{
            Product produto = productService.createProduct(productCreateDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(produto);
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductImageResponseDTO> upload(@Positive(message = "O ID do Product tem que ser maior que 0") @PathVariable Long productId, @RequestPart("file") MultipartFile file, @RequestParam(name = "imagemPrincipal", defaultValue = "false") Boolean imagemPrincipal
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
    public ResponseEntity<List<ProductImageResponseDTO>> list(@Positive(message = "O ID do Product tem que ser maior que 0") @PathVariable Integer productId){
        return ResponseEntity.ok(
                productImageService.listByProduct(productId)
        );
    }

    @GetMapping
    public ResponseEntity<?> fetchAllProducts() {
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
    public ProductResponseDTO searchForOneProduct(@Positive(message = "O ID do Product tem que ser maior que 0") @PathVariable Integer productId){
        return productService.findOneProduct(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduto(@Positive(message = "O ID do Product tem que ser maior que 0") @PathVariable Integer productId){
        ProductResponseDTO dto = productService.deleteOneProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> remove(@Positive(message = "O ID do Image tem que ser maior que 0") @PathVariable Long imageId) {
        productImageService.remover(imageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponseDTO>> listProduct(@PageableDefault(sort = "id") Pageable pageable) {

        Page<ProductResponseDTO> produtos = productService.findAllPaginado(pageable);

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<List<ProductResponseDTO>> listProductsByCategory(@Positive(message = "O ID do Product tem que ser maior que 0") @PathVariable Long idCategory) {
        List<ProductResponseDTO> produtos = productService.listProductsByCategory(idCategory);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProductResponseDTO>> listProductsByName(@NotBlank(message = "O Nome não pode ser vazio")  @PathVariable String nome) {
        List<ProductResponseDTO> dto = productService.searchProductByName(nome);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<ProductResponseDTO>> listProductsByPrice() {
        List<ProductResponseDTO> dto = productService.searchProductByPriceAndOrder();
        return  ResponseEntity.status(HttpStatus.OK).body(dto);

    }

    @PatchMapping("/{idProduct}")
    public ResponseEntity<ProductResponseDTO> updateDataProduct(@Positive(message = "O ID do Product tem que ser maior que 0") @PathVariable Integer idProduct, @Valid @RequestBody ProductUpdateDTO productUpdateDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.updateProductData(idProduct, productUpdateDTO));
    }
}
