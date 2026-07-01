package com.miguelpazatto.orderapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDTO(
        @NotNull(message = "O ID é obrigatório") @Positive(message = "ID tem que ser maior que zero") Long productId,

        @NotNull(message = "A quantidade é obrigatória") @Positive(message = "Quantidade do produto tem que ser positiva") Integer quantity) {


}
