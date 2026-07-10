package com.example.ecommerce.modules.product.service;

import com.example.ecommerce.externalServices.storage.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class LocalImageStorageService implements ImageStorageService {
    private static final long TAMANHO_MAXIMO = 5 * 1024 * 1024; // 5 MB

    private static final Set<String> TIPOS_PERMITIDOS = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final Path uploadDirectory;
    private final String baseUrl;

    public LocalImageStorageService(
            @Value("${app.upload.directory}") String uploadDirectory,
            @Value("${app.upload.base-url}") String baseUrl
    ) {
        this.uploadDirectory = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        this.baseUrl = baseUrl;

        try {
            Files.createDirectories(this.uploadDirectory);
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Não foi possível criar o diretório de imagens",
                    exception
            );
        }
    }

    @Override
    public String upload(MultipartFile file) {
        validarArquivo(file);

        String extensao = extrairExtensao(file.getOriginalFilename());
        String novoNomeArquivo = UUID.randomUUID() + extensao;

        Path destino = uploadDirectory
                .resolve(novoNomeArquivo)
                .normalize();

        if (!destino.startsWith(uploadDirectory)) {
            throw new RuntimeException("Caminho de arquivo inválido");
        }

        try {
            Files.copy(
                    file.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException exception) {
            throw new RuntimeException("Erro ao salvar a imagem", exception);
        }

        return baseUrl + "/" + novoNomeArquivo;
    }

    @Override
    public void delete(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        String nomeArquivo = imageUrl.substring(
                imageUrl.lastIndexOf("/") + 1
        );

        Path arquivo = uploadDirectory
                .resolve(nomeArquivo)
                .normalize();

        if (!arquivo.startsWith(uploadDirectory)) {
            throw new RuntimeException("Caminho de arquivo inválido");
        }

        try {
            Files.deleteIfExists(arquivo);
        } catch (IOException exception) {
            throw new RuntimeException("Erro ao remover a imagem", exception);
        }
    }

    private void validarArquivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Imagem não informada");
        }

        if (file.getSize() > TAMANHO_MAXIMO) {
            throw new RuntimeException(
                    "A imagem não pode ultrapassar 5 MB"
            );
        }

        String contentType = file.getContentType();

        if (contentType == null || !TIPOS_PERMITIDOS.contains(contentType)) {
            throw new RuntimeException(
                    "Formato inválido. Envie uma imagem JPG, PNG ou WEBP"
            );
        }
    }

    private String extrairExtensao(String nomeOriginal) {
        if (nomeOriginal == null || !nomeOriginal.contains(".")) {
            throw new RuntimeException("Arquivo sem extensão válida");
        }

        return nomeOriginal.substring(
                nomeOriginal.lastIndexOf(".")
        ).toLowerCase();
    }
}
