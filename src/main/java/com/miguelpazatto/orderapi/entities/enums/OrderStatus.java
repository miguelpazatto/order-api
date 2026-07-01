package com.miguelpazatto.orderapi.entities.enums;

public enum OrderStatus {
    PENDING("Pedido recebido. Aguardando processamento do pagamento."),
    PAID("Pagamento confirmado. Preparando para envio."),
    REFUSED("Pagamento recusado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
