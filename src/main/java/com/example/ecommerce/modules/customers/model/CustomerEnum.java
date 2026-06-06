package com.example.ecommerce.modules.customers.model;

public enum CustomerEnum {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    BLOQUEADO("Bloqueado");

    private String customerEcommerce;
    CustomerEnum(String customerEcommerce) {
        this.customerEcommerce = customerEcommerce;
    }
    public static CustomerEnum fromString(String text) {
        for (CustomerEnum categoria : CustomerEnum.values()) {
            if (categoria.customerEcommerce.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
