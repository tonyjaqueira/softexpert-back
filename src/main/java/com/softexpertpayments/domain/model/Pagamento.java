package com.softexpertpayments.domain.model;

import lombok.Builder;

@Builder
public record Pagamento(
        String cliente,
        String valor,
        String link
) {
}
