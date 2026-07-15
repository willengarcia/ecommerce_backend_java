package com.example.ecommerce.modules.product.model;

public enum ProductEnum {
    ATIVO("true"),
    INATIVO("false"),
    SEM_ESTOQUE("SEM_ESTOQUE");

    private final String productEnum;
    ProductEnum(String productEnum) {
        this.productEnum = productEnum;
    }
    public static ProductEnum fromString(String text) {
        for (ProductEnum categoria : ProductEnum.values()) {
            if (categoria.productEnum.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
