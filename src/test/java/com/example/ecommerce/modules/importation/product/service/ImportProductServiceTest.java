package com.example.ecommerce.modules.importation.product.service;

import com.example.ecommerce.modules.importation.product.dto.ImportProductRowDTO;
import com.example.ecommerce.modules.importation.product.exception.ImportFileException;
import com.example.ecommerce.modules.importation.product.exception.ImportValidationException;
import com.example.ecommerce.modules.importation.product.exception.InvalidCsvException;
import com.example.ecommerce.modules.importation.product.mapper.ImportProductMapper;
import com.example.ecommerce.modules.product.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportProductServiceTest {

    @Mock
    private ImportProductMapper productImportMapper;

    @Mock
    private ProductService productService;

    @Mock
    private Validator validator;

    @InjectMocks
    private ImportProductService importProductService;


    @Test
    @DisplayName("Deve lançar InvalidCsvException quando o arquivo for nulo")
    void returnExceptionWhenFileIsNull() {

        InvalidCsvException exception =
                Assertions.assertThrows(
                        InvalidCsvException.class,
                        () -> importProductService.importFile(null)
                );

        Assertions.assertEquals(
                "O arquivo CSV não pode estar vazio",
                exception.getMessage()
        );

        verifyNoInteractions(
                productImportMapper,
                productService,
                validator
        );
    }


    @Test
    @DisplayName("Deve lançar InvalidCsvException quando o arquivo estiver vazio")
    void returnExceptionWhenFileIsEmpty() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.csv",
                        "text/csv",
                        new byte[0]
                );

        InvalidCsvException exception =
                Assertions.assertThrows(
                        InvalidCsvException.class,
                        () -> importProductService.importFile(file)
                );

        Assertions.assertEquals(
                "O arquivo CSV não pode estar vazio",
                exception.getMessage()
        );

        verifyNoInteractions(
                productImportMapper,
                productService,
                validator
        );
    }


    @Test
    @DisplayName("Deve lançar InvalidCsvException quando o arquivo não estiver no formato CSV")
    void returnExceptionWhenFileIsNotCsv() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "conteudo".getBytes(StandardCharsets.UTF_8)
                );

        InvalidCsvException exception =
                Assertions.assertThrows(
                        InvalidCsvException.class,
                        () -> importProductService.importFile(file)
                );

        Assertions.assertEquals(
                "O arquivo deve estar no formato CSV",
                exception.getMessage()
        );

        verifyNoInteractions(
                productImportMapper,
                productService,
                validator
        );
    }


    @Test
    @DisplayName("Deve lançar InvalidCsvException quando faltar uma coluna obrigatória")
    void returnExceptionWhenRequiredHeaderIsMissing() {

        String csv = """
                nome;slug;descricaoCurta;descricao;preco;precoPromocional;quantidadeEstoque;estoqueMinimo;sku;peso;altura;largura;comprimento;status
                Notebook;notebook;Notebook teste;Descrição;1500.00;;10;2;NOTE-001;2.5;10;30;20;ATIVO
                """;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.csv",
                        "text/csv",
                        csv.getBytes(StandardCharsets.UTF_8)
                );

        InvalidCsvException exception =
                Assertions.assertThrows(
                        InvalidCsvException.class,
                        () -> importProductService.importFile(file)
                );

        Assertions.assertEquals(
                "A coluna obrigatória 'categoriaId' não foi encontrada",
                exception.getMessage()
        );

        verifyNoInteractions(
                productImportMapper,
                productService,
                validator
        );
    }


    @Test
    @DisplayName("Deve lançar ImportValidationException quando o mapper receber um valor inválido")
    void returnExceptionWhenCsvValueHasInvalidFormat() {

        String csv = """
                nome;slug;descricaoCurta;descricao;preco;precoPromocional;quantidadeEstoque;estoqueMinimo;sku;peso;altura;largura;comprimento;status;categoriaId
                Notebook;notebook;Notebook teste;Descrição;valor-invalido;;10;2;NOTE-001;2.5;10;30;20;ATIVO;1
                """;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.csv",
                        "text/csv",
                        csv.getBytes(StandardCharsets.UTF_8)
                );

        when(productImportMapper.toDTO(any(CSVRecord.class)))
                .thenThrow(new IllegalArgumentException());

        ImportValidationException exception =
                Assertions.assertThrows(
                        ImportValidationException.class,
                        () -> importProductService.importFile(file)
                );

        Assertions.assertTrue(
                exception.getMessage().contains(
                        "A linha possui um valor inválido ou em formato incorreto"
                )
        );

        verify(productService, never())
                .createFromImport(any());
    }


    @Test
    @DisplayName("Deve lançar ImportValidationException quando o DTO possuir campos inválidos")
    void returnExceptionWhenDtoValidationFails() {

        String csv = """
                nome;slug;descricaoCurta;descricao;preco;precoPromocional;quantidadeEstoque;estoqueMinimo;sku;peso;altura;largura;comprimento;status;categoriaId
                Notebook;notebook;Notebook teste;Descrição;1500.00;;10;2;NOTE-001;2.5;10;30;20;ATIVO;1
                """;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.csv",
                        "text/csv",
                        csv.getBytes(StandardCharsets.UTF_8)
                );

        ImportProductRowDTO dto = mock(ImportProductRowDTO.class);

        @SuppressWarnings("unchecked")
        ConstraintViolation<ImportProductRowDTO> violation =
                mock(ConstraintViolation.class);

        when(productImportMapper.toDTO(any(CSVRecord.class)))
                .thenReturn(dto);

        when(validator.validate(dto))
                .thenReturn(Set.of(violation));

        when(violation.getMessage())
                .thenReturn("Preço deve ser maior que zero");

        ImportValidationException exception =
                Assertions.assertThrows(
                        ImportValidationException.class,
                        () -> importProductService.importFile(file)
                );

        Assertions.assertTrue(
                exception.getMessage().contains(
                        "Preço deve ser maior que zero"
                )
        );

        verify(productService, never())
                .createFromImport(any());
    }


    @Test
    @DisplayName("Deve importar o produto quando o arquivo CSV for válido")
    void importProductSuccessfully() {

        String csv = """
                nome;slug;descricaoCurta;descricao;preco;precoPromocional;quantidadeEstoque;estoqueMinimo;sku;peso;altura;largura;comprimento;status;categoriaId
                Notebook;notebook;Notebook teste;Descrição;1500.00;;10;2;NOTE-001;2.5;10;30;20;ATIVO;1
                """;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.csv",
                        "text/csv",
                        csv.getBytes(StandardCharsets.UTF_8)
                );

        ImportProductRowDTO dto = mock(ImportProductRowDTO.class);

        when(productImportMapper.toDTO(any(CSVRecord.class)))
                .thenReturn(dto);

        when(validator.validate(dto))
                .thenReturn(Collections.emptySet());

        importProductService.importFile(file);

        verify(productImportMapper)
                .toDTO(any(CSVRecord.class));

        verify(validator)
                .validate(dto);

        verify(productService)
                .createFromImport(dto);
    }


    @Test
    @DisplayName("Deve importar todos os produtos quando o CSV possuir várias linhas válidas")
    void importMultipleProductsSuccessfully() {

        String csv = """
                nome;slug;descricaoCurta;descricao;preco;precoPromocional;quantidadeEstoque;estoqueMinimo;sku;peso;altura;largura;comprimento;status;categoriaId
                Notebook;notebook;Notebook teste;Descrição;1500.00;;10;2;NOTE-001;2.5;10;30;20;ATIVO;1
                Mouse;mouse;Mouse teste;Descrição;100.00;;20;3;MOUSE-001;0.2;5;10;15;ATIVO;1
                """;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "product.csv",
                        "text/csv",
                        csv.getBytes(StandardCharsets.UTF_8)
                );

        ImportProductRowDTO dto1 =
                mock(ImportProductRowDTO.class);

        ImportProductRowDTO dto2 =
                mock(ImportProductRowDTO.class);

        when(productImportMapper.toDTO(any(CSVRecord.class)))
                .thenReturn(dto1, dto2);

        when(validator.validate(dto1))
                .thenReturn(Collections.emptySet());

        when(validator.validate(dto2))
                .thenReturn(Collections.emptySet());

        importProductService.importFile(file);

        verify(productImportMapper, times(2))
                .toDTO(any(CSVRecord.class));

        verify(validator)
                .validate(dto1);

        verify(validator)
                .validate(dto2);

        verify(productService)
                .createFromImport(dto1);

        verify(productService)
                .createFromImport(dto2);
    }
}