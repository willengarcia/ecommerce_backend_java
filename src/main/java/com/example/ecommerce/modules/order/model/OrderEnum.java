package com.example.ecommerce.modules.order.model;

public enum OrderEnum {
    CRIADO("ATIVO"),
    AGUARDANDO_PAGAMENTO("CONVERTIDO"),
    PAGO("ABANDONADO"),
    PROCESSADO("PROCESSADO"),
    SEPARADO("SEPARADO"),
    PREPARANDO("PREPARANDO"),
    ENVIADO("ENVIADO"),
    ENTREGUE("ENTREGUE"),
    CANCELADO("CANCELADO"),
    REEMBOLSADO("REEMBOLSADO");
    private String orderEnum;
    OrderEnum(String orderEnum) {
        this.orderEnum = orderEnum;
    }
    public static OrderEnum fromString(String text) {
        for (OrderEnum categoria : OrderEnum.values()) {
            if (categoria.orderEnum.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
