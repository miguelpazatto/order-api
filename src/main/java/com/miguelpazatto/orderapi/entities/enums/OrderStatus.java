package com.miguelpazatto.orderapi.entities.enums;

public enum OrderStatus {
    PENDING("Pedido recebido. Aguardando processamento do pagamento."),
    PAID("Pagamento confirmado. Preparando para envio."),
    REFUSED("Pagamento recusado"),
    CANCELED("Pedido cancelado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
