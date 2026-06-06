package com.example.ecommerce.modules.address.model;

public enum AddressEnum {
    CASA("Casa"),
    TRABALHO("Trabalho"),
    OUTRO("Outro"),;
    private String addressEnum;
    AddressEnum(String productEnum) {
        this.addressEnum = productEnum;
    }
    public static AddressEnum fromString(String text) {
        for (AddressEnum categoria : AddressEnum.values()) {
            if (categoria.addressEnum.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
