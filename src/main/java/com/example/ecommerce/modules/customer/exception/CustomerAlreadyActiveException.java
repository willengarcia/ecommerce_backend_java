package com.example.ecommerce.modules.customer.exception;

public class CustomerAlreadyActiveException extends RuntimeException {
    public CustomerAlreadyActiveException(String message) {
        super(message);
    }
}
