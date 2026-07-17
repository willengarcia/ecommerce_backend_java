package com.example.ecommerce.modules.importation.product.exception;

public class InvalidCsvException extends RuntimeException {
    public InvalidCsvException(String message) {
        super(message);
    }
}
