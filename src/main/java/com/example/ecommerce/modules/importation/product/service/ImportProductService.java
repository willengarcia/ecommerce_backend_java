package com.example.ecommerce.modules.importation.product.service;

import com.example.ecommerce.modules.importation.product.dto.ImportProductRowDTO;
import com.example.ecommerce.modules.importation.product.exception.ImportFileException;
import com.example.ecommerce.modules.importation.product.exception.ImportValidationException;
import com.example.ecommerce.modules.importation.product.exception.InvalidCsvException;
import com.example.ecommerce.modules.importation.product.mapper.ImportProductMapper;
import com.example.ecommerce.modules.product.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImportProductService {
    private final ImportProductMapper productImportMapper;
    private final ProductService productService;
    private final Validator validator;

    public ImportProductService(
            ImportProductMapper productImportMapper,
            ProductService productService, Validator validator
    ) {
        this.productImportMapper = productImportMapper;
        this.productService = productService;
        this.validator = validator;
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
                long line = record.getRecordNumber() + 1;

                ImportProductRowDTO dto;

                try {
                    dto = productImportMapper.toDTO(record);
                } catch (IllegalArgumentException exception) {
                    throw new ImportValidationException(
                            line,
                            "A linha possui um valor inválido ou em formato incorreto"
                    );
                }

                validateValues(dto, line);
                productService.createFromImport(dto);
            }

        } catch (IOException exception) {
            throw new ImportFileException(
                    "Não foi possível ler o arquivo CSV"
            );
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidCsvException(
                    "O arquivo CSV não pode estar vazio"
            );
        }
        String filename = file.getOriginalFilename();

        if (filename == null
                || !filename.toLowerCase().endsWith(".csv")) {
            throw new InvalidCsvException(
                    "O arquivo deve estar no formato CSV"
            );
        }
    }

    private void validateValues(ImportProductRowDTO dto, long line) {
        Set<ConstraintViolation<ImportProductRowDTO>> violations =
                validator.validate(dto);

        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining("; "));

            throw new ImportValidationException(line, message);
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
                throw new InvalidCsvException(
                        "A coluna obrigatória '" + header
                                + "' não foi encontrada"
                );
            }
        }
    }
}
