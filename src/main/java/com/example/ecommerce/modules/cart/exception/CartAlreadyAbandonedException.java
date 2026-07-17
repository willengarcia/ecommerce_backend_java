package com.example.ecommerce.modules.cart.exception;

public class CartAlreadyAbandonedException extends RuntimeException {
    public CartAlreadyAbandonedException(String message) {
        super(message);
    }

    public static class CartOwnershipException extends RuntimeException {
        public CartOwnershipException(String message) {
            super(message);
        }
    }
}
