package com.softexpertpayments.domain.model.response;

import lombok.Builder;

@Builder
public record PaymentLinksResponse(
       String id,
       String url
) {
}
