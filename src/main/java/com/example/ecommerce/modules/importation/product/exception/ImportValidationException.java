package com.example.ecommerce.modules.importation.product.exception;

public class ImportValidationException extends RuntimeException {
    public ImportValidationException(long line, String message) {
        super(message);
    }
}
