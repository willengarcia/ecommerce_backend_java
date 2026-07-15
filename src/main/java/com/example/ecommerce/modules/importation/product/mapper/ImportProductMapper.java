package com.example.ecommerce.modules.importation.product.mapper;

import com.example.ecommerce.modules.importation.product.dto.ImportProductRowDTO;
import com.example.ecommerce.modules.importation.product.exception.ProductImportException;
import com.example.ecommerce.modules.product.model.ProductEnum;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.lang.Float.parseFloat;

@Component
public class ImportProductMapper {
    public ImportProductRowDTO toDTO(CSVRecord record) {
        long line = record.getRecordNumber() + 1;

        return new ImportProductRowDTO(
                requiredString(record, "nome", line),
                requiredString(record, "slug", line),
                optionalString(record, "descricaoCurta"),
                optionalString(record, "descricao"),
                requiredBigDecimal(record, "preco", line),
                optionalBigDecimal(record, "precoPromocional", line),
                requiredInteger(record, "quantidadeEstoque", line),
                requiredInteger(record, "estoqueMinimo", line),
                requiredString(record, "sku", line),
                requiredFloat(record, "peso", line),
                requiredFloat(record, "altura", line),
                requiredFloat(record, "largura", line),
                requiredFloat(record, "comprimento", line),
                requiredStatus(record, "status", line),
                requiredLong(record, "categoriaId", line)
        );
    }

    private String requiredString(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = getValue(record, column, line);

        if (value.isBlank()) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' é obrigatória"
            );
        }

        return value;
    }
    private ProductEnum requiredStatus(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = requiredString(record, column, line);

        try {
            return ProductEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new ProductImportException(
                    line,
                    "Status inválido: '" + value + "'"
            );
        }
    }

    private String optionalString(CSVRecord record, String column) {
        String value = record.get(column);

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private BigDecimal requiredBigDecimal(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = requiredString(record, column, line);

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException exception) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' deve ser um número decimal",
                    exception
            );
        }
    }

    private BigDecimal optionalBigDecimal(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = getValue(record, column, line);

        if (value.isBlank()) {
            return null;
        }

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException exception) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' deve ser um número decimal",
                    exception
            );
        }
    }

    private Float requiredFloat(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = getValue(record, column, line);

        if (value.isBlank()) {
            return null;
        }

        try {
            return parseFloat(value);
        } catch (NumberFormatException exception) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' deve ser um número decimal",
                    exception
            );
        }
    }

    private Integer requiredInteger(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = requiredString(record, column, line);

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException exception) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' deve ser um número inteiro",
                    exception
            );
        }
    }

    private Long requiredLong(
            CSVRecord record,
            String column,
            long line
    ) {
        String value = requiredString(record, column, line);

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' deve ser um número inteiro",
                    exception
            );
        }
    }

    private String getValue(
            CSVRecord record,
            String column,
            long line
    ) {
        try {
            String value = record.get(column);
            return value == null ? "" : value.trim();
        } catch (IllegalArgumentException exception) {
            throw new ProductImportException(
                    line,
                    "A coluna '" + column + "' não foi encontrada no arquivo",
                    exception
            );
        }
    }
}
