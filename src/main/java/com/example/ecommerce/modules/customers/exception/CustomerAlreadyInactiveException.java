package com.example.ecommerce.modules.customers.exception;

public class CustomerAlreadyInactiveException extends RuntimeException {
    public CustomerAlreadyInactiveException(String message) {
        super(message);
    }
}
