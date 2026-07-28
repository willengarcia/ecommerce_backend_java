package com.example.ecommerce.modules.customer.exception;

public class DuplicateCpfException extends RuntimeException {
    public DuplicateCpfException(String message) {
        super(message);
    }
}
