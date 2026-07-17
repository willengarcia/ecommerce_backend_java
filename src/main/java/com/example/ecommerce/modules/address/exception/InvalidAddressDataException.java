package com.example.ecommerce.modules.address.exception;

public class InvalidAddressDataException extends RuntimeException {
    public InvalidAddressDataException(String message) {
        super(message);
    }
}
