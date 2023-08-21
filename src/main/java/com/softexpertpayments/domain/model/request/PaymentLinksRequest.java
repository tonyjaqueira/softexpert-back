package com.softexpertpayments.domain.model.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record PaymentLinksRequest(

        String billingType,
        String chargeType,
        Integer dueDateLimitDays,
        String name,
        BigDecimal value
) {

}
