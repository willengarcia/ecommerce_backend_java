package com.example.ecommerce.modules.address.exception;

public class DefaultAddressAlreadyExistsException extends RuntimeException {
    public DefaultAddressAlreadyExistsException(String message) {
        super(message);
    }
}
