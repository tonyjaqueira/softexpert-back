package com.softexpertpayments.client;


import com.softexpertpayments.domain.model.request.PaymentLinksRequest;
import com.softexpertpayments.domain.model.response.PaymentLinksResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "asaas", url = "${api-asaas-host}")
public interface AssasClient {

    @PostMapping("/paymentLinks")
    PaymentLinksResponse gerarLinkPagamento(@RequestBody PaymentLinksRequest requestBody, @RequestHeader("access_token") String autorizador);

}
