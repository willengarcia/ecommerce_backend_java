package com.example.ecommerce.modules.customer.exception;

public class CustomerHasOpenOrdersException extends RuntimeException {
    public CustomerHasOpenOrdersException(String message) {
        super(message);
    }
}
