package com.example.ecommerce.modules.customer.exception;

public class InactiveCustomerException extends RuntimeException {
    public InactiveCustomerException(String message) {
        super(message);
    }
}
