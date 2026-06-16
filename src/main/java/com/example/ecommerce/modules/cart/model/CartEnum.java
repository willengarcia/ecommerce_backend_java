package com.example.ecommerce.modules.cart.model;


public enum CartEnum {
    ATIVO("ATIVO"),
    CONVERTIDO("CONVERTIDO"),
    ABANDONADO("ABANDONADO");
    private String cartEnum;
    CartEnum(String cartEnum) {
        this.cartEnum = cartEnum;
    }
    public static CartEnum fromString(String text) {
        for (CartEnum categoria : CartEnum.values()) {
            if (categoria.cartEnum.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
