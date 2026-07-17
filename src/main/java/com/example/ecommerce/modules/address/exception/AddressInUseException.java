package com.example.ecommerce.modules.address.exception;

public class AddressInUseException extends RuntimeException {
    public AddressInUseException(String message) {
        super(message);
    }
}
