package com.softexpertpayments.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Builder
@Validated
public record Item(
        @NotBlank(message = "Por favor informe uma descrição para o item do pedido!")
        String descricao,
        @NotBlank(message = "Por favor informe o valor do item do pedido!")
        BigDecimal valorItem,
        @NotBlank(message = "Por favor informe quem pediu o item do pedido!")
        String cliente
) {
}
