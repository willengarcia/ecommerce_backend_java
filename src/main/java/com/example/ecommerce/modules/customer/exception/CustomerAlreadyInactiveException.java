package com.example.ecommerce.modules.customer.exception;

public class CustomerAlreadyInactiveException extends RuntimeException {
    public CustomerAlreadyInactiveException(String message) {
        super(message);
    }
}
