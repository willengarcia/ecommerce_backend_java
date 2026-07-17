package com.example.ecommerce.modules.product.service;

import com.example.ecommerce.externalServices.storage.ImageStorageService;
import com.example.ecommerce.modules.product.dto.ProductImageResponseDTO;
import com.example.ecommerce.modules.product.exception.ImageNotFoundException;
import com.example.ecommerce.modules.product.exception.ProductNotFoundException;
import com.example.ecommerce.modules.product.model.Product;
import com.example.ecommerce.modules.product.model.ProductImages;
import com.example.ecommerce.modules.product.repository.ProductImageRepository;
import com.example.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ImageStorageService imageStorageService;

    public ProductImageService(
            ProductImageRepository productImageRepository,
            ProductRepository productRepository,
            ImageStorageService imageStorageService
    ) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.imageStorageService = imageStorageService;
    }

    @Transactional
    public ProductImageResponseDTO upload(
            Long productId,
            MultipartFile file,
            Boolean imagemPrincipal
    ) {
        Product product = productRepository.findById(productId);

        boolean principal = Boolean.TRUE.equals(imagemPrincipal);

        /*
         * Primeira imagem do produto vira principal automaticamente.
         */
        List<ProductImages> imagensExistentes =
                productImageRepository.findByProductId(productId);

        if (imagensExistentes.isEmpty()) {
            principal = true;
        }

        /*
         * Caso a nova imagem seja principal,
         * desmarca a principal anterior.
         */
        if (principal) {
            imagensExistentes.forEach(imagem -> {
                if (Boolean.TRUE.equals(imagem.getImagemPrincipal())) {
                    imagem.setImagemPrincipal(false);
                    imagem.setDataAtualizacao(LocalDate.now());
                }
            });

            productImageRepository.saveAll(imagensExistentes);
        }

        String urlImagem = imageStorageService.upload(file);

        ProductImages productImage = new ProductImages();
        productImage.setNomeImagem(file.getOriginalFilename());
        productImage.setUrlImagem(urlImagem);
        productImage.setImagemPrincipal(principal);
        productImage.setDataCriacao(LocalDate.now());
        productImage.setProduct(product);

        ProductImages imagemSalva =
                productImageRepository.save(productImage);

        return toResponseDTO(imagemSalva);
    }

    public List<ProductImageResponseDTO> listarPorProduto(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Produto não encontrado");
        }

        return productImageRepository.findByProductId(Long.valueOf(productId))
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public void remover(Long imageId) {
        ProductImages image = productImageRepository.findById(imageId)
                .orElseThrow(() ->
                        new ImageNotFoundException("Imagem não encontrada")
                );

        imageStorageService.delete(image.getUrlImagem());
        productImageRepository.delete(image);
    }

    private ProductImageResponseDTO toResponseDTO(ProductImages image) {
        return new ProductImageResponseDTO(
                image.getId(),
                image.getNomeImagem(),
                image.getUrlImagem(),
                image.getImagemPrincipal(),
                image.getDataCriacao(),
                image.getProduct().getId()
        );
    }
}
