package com.example.ecommerce.modules.customers.exception;

public class CustomerAlreadyActiveException extends RuntimeException {
    public CustomerAlreadyActiveException(String message) {
        super(message);
    }
}
