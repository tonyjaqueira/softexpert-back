package com.softexpertpayments.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public record Rateio(
        BigDecimal valorTotalPedido,
        Map<String, BigDecimal> valorPorPessoa,
        List<Pagamento> linksPagamento
) {
}
