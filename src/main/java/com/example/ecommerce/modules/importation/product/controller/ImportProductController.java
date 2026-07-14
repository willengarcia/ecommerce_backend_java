package com.example.ecommerce.modules.importation.product.controller;

import com.example.ecommerce.modules.importation.product.service.ImportProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imports")
public class ImportProductController {
    private final ImportProductService importProductService;

    public ImportProductController(ImportProductService importProductService) {
        this.importProductService = importProductService;
    }
    @PostMapping("/products")
    public ResponseEntity<String> importProducts(
            @RequestParam("file") MultipartFile file
    ) {
        importProductService.importFile(file);

        return ResponseEntity.ok("Arquivo processado com sucesso");
    }
}
