package com.example.ecommerce.modules.cart.exception;

public class CartIsActiveFromCustomer extends RuntimeException {
    public CartIsActiveFromCustomer(String message) {
        super(message);
    }
}
