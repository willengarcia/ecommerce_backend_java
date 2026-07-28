package com.example.ecommerce.modules.category.exception;

public class InactiveCategoryException extends RuntimeException {
    public InactiveCategoryException(String message) {
        super(message);
    }
}
