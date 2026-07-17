package com.example.ecommerce.modules.customers.exception;

public class CustomerHasOpenOrdersException extends RuntimeException {
    public CustomerHasOpenOrdersException(String message) {
        super(message);
    }
}
