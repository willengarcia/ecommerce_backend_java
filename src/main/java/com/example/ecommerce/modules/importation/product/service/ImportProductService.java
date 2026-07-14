package com.example.ecommerce.modules.importation.product.service;

import com.example.ecommerce.modules.importation.product.dto.ImportProductRowDTO;
import com.example.ecommerce.modules.importation.product.exception.ProductImportException;
import com.example.ecommerce.modules.importation.product.mapper.ImportProductMapper;
import com.example.ecommerce.modules.product.service.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
public class ImportProductService {
    private final ImportProductMapper productImportMapper;
    private final ProductService productService;

    public ImportProductService(
            ImportProductMapper productImportMapper,
            ProductService productService
    ) {
        this.productImportMapper = productImportMapper;
        this.productService = productService;
    }

    @Transactional
    public void importFile(MultipartFile file) {
        validateFile(file);

        try (
                Reader reader = new InputStreamReader(
                        file.getInputStream(),
                        StandardCharsets.UTF_8
                );

                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setDelimiter(';')
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .setIgnoreEmptyLines(true)
                        .get()
                        .parse(reader)
        ) {
            validateHeader(parser);

            for (CSVRecord record : parser) {
                ImportProductRowDTO dto = productImportMapper.toDTO(record);

                validateValues(dto, record.getRecordNumber() + 1);

                productService.createFromImport(dto);
            }

        } catch (ProductImportException exception) {
            throw exception;
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Não foi possível ler o arquivo CSV",
                    exception
            );
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "O arquivo CSV não pode estar vazio"
            );
        }
    }

    private void validateValues(ImportProductRowDTO dto, long line) {
        if (dto.preco() < 0) {
            throw new ProductImportException(
                    line,
                    "O preço não pode ser negativo"
            );
        }

        if (dto.precoPromocional() != null
                && dto.precoPromocional().compareTo(dto.preco()) >= 0) {
            throw new ProductImportException(
                    line,
                    "O preço promocional deve ser menor que o preço normal"
            );
        }

        if (dto.quantidadeEstoque() < 0) {
            throw new ProductImportException(
                    line,
                    "A quantidade em estoque não pode ser negativa"
            );
        }

        if (dto.estoqueMinimo() < 0) {
            throw new ProductImportException(
                    line,
                    "O estoque mínimo não pode ser negativo"
            );
        }
    }

    private void validateHeader(CSVParser parser) {
        String[] requiredHeaders = {
                "nome",
                "slug",
                "descricaoCurta",
                "descricao",
                "preco",
                "precoPromocional",
                "quantidadeEstoque",
                "estoqueMinimo",
                "sku",
                "peso",
                "altura",
                "largura",
                "comprimento",
                "status",
                "categoriaId"
        };

        for (String header : requiredHeaders) {
            if (!parser.getHeaderMap().containsKey(header)) {
                throw new IllegalArgumentException(
                        "A coluna obrigatória '" + header
                                + "' não foi encontrada"
                );
            }
        }
    }
}
