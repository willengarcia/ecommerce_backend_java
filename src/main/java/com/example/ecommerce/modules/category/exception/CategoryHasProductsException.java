package com.example.ecommerce.modules.category.exception;

public class CategoryHasProductsException extends RuntimeException {
    public CategoryHasProductsException(String message) {
        super(message);
    }
}
