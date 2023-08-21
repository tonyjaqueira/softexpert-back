package com.softexpertpayments.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record Pedido(
        @NotBlank(message = "Por favor informe o valor da taxa de entrega.")
        BigDecimal valorEntrega,
        @NotBlank(message = "Por favor informe o valor do desconto caso exista.")
        BigDecimal valorDesconto,
        @Valid
        @NotNull
        List<Item> itensPedido
) {
}
