package com.miguelpazatto.orderapi.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "Favor inserir nome do produto")
        String name,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "Atribua um preço positivo ao produto")
        @Digits(integer = 6, fraction = 2, message = "O preço deve ter no máximo 6 dígitos inteiros e 2 casas decimais")
        BigDecimal price,

        @NotNull(message = "O estoque é obrigatório")
        @PositiveOrZero(message = "Atribua um valor de estoque igual ou maior que zero ao produto")
        Integer availableStock
) {
}
