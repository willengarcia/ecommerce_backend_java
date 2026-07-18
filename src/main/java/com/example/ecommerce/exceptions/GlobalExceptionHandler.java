package com.example.ecommerce.exceptions;

import com.example.ecommerce.modules.address.exception.*;
import com.example.ecommerce.modules.cart.exception.*;
import com.example.ecommerce.modules.category.exceptions.*;
import com.example.ecommerce.modules.checkout.exception.EmptyCartException;
import com.example.ecommerce.modules.customers.exception.*;
import com.example.ecommerce.modules.importation.product.exception.ImportFileException;
import com.example.ecommerce.modules.importation.product.exception.ImportValidationException;
import com.example.ecommerce.modules.importation.product.exception.InvalidCsvException;
import com.example.ecommerce.modules.order.exception.*;
import com.example.ecommerce.modules.product.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Recurso não encontrado
    @ExceptionHandler({
            ProductNotFoundException.class,
            CategoryNotFoundException.class,
            ImageNotFoundException.class,
            CustomerNotFoundException.class,
            AddressNotFoundException.class,
            CartItemNotFoundException.class,
            CartNotFoundException.class,
            OrderNotFoundException.class
    })
    public ResponseEntity<?> exceptionsNotFound(RuntimeException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // 409 - Conflito com o estado atual do sistema
    @ExceptionHandler({
            DuplicateProductException.class,
            InsufficientStockException.class,
            DuplicateCpfException.class,
            DuplicateEmailException.class,
            CustomerAlreadyActiveException.class,
            CustomerAlreadyInactiveException.class,
            CustomerHasOpenOrdersException.class,
            DefaultAddressAlreadyExistsException.class,
            AddressInUseException.class,
            CartAlreadyAbandonedException.class,
            CartIsActiveFromCustomer.class,
            InvalidOrderStatusException.class,
            DuplicateCategoryException.class,
            CategoryHasProductsException.class
    })
    public ResponseEntity<?> exceptionsConflict(RuntimeException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // 400 - Dados enviados são inválidos
    @ExceptionHandler({
            InvalidProductDataException.class,
            InvalidImageException.class,
            ImportFileException.class,
            InvalidCsvException.class,
            InvalidCustomerDataException.class,
            InvalidAddressDataException.class,
            InvalidZipCodeException.class,
            InvalidOrderDataException.class,
            InvalidCategoryDataException.class
    })
    public ResponseEntity<?> exceptionsBadRequest(RuntimeException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 422 - Dados compreendidos, mas impedidos por regra de negócio
    @ExceptionHandler({
            InactiveProductException.class,
            InactiveCategoryException.class,
            ImportValidationException.class,
            InactiveCustomerException.class,
            EmptyCartException.class
    })
    public ResponseEntity<?> exceptionsUnprocessableEntity(RuntimeException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(response);
    }

    // 403 - Cliente não possui permissão sobre o recurso
    @ExceptionHandler({
            AddressOwnershipException.class,
            CartOwnershipException.class,
            OrderOwnershipException.class,
            OrderAddressOwnershipException.class
    })
    public ResponseEntity<?> exceptionsForbidden(RuntimeException e) {
        ResponseError response = new ResponseError(
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    // 500 - Falha interna ao realizar upload
    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<?> exceptionsImageUpload(ImageUploadException e) {
        ResponseError response = new ResponseError(
                "Ocorreu um erro interno ao realizar o upload da imagem.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionAll(Exception e){ // método coringa para tratamento de erros
        ResponseError response = new ResponseError(
                "Ocorreu um erro interno ao processar a solicitação.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
