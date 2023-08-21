package com.softexpertpayments.domain.service;

import com.softexpertpayments.client.AssasClient;
import com.softexpertpayments.domain.model.Pagamento;
import com.softexpertpayments.domain.model.request.PaymentLinksRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService{

    private final AssasClient assasClient;
    private static final String BILLING_TYPE = "UNDEFINED";
    private static final String CHARGE_TYPE = "DETACHED";
    private static final Integer DUE_DATE_LIMIT_DAYS = 10;
    private final String accessToken;


    public PagamentoServiceImpl(AssasClient assasClient, @Value("${api-asaas-token}") String accessToken) {
        this.assasClient = assasClient;
        this.accessToken = accessToken;
    }

    @Override
    public List<Pagamento> criarLinkPagamentoAssas(Map<String, BigDecimal> lista) {
        var listsPagamento = new ArrayList<Pagamento>();
        try {
            log.info("### Iniciando criação de link de pagamento ###");
            lista.forEach((key, value) -> {
                var paymentLinksRequest =
                        PaymentLinksRequest.builder()
                                .billingType(BILLING_TYPE)
                                .chargeType(CHARGE_TYPE)
                                .dueDateLimitDays(DUE_DATE_LIMIT_DAYS)
                                .name(key)
                                .value(value).build();
                var response = assasClient.gerarLinkPagamento(paymentLinksRequest, accessToken);
                listsPagamento.add(Pagamento.builder().link(response.url()).valor(formatarValor(value)).cliente(key).build());
                log.info("Link criado: {}", response.toString());
            });
            log.info("### Finalizando criação de link de pagamento ###");

        }catch (Exception e){
            log.error("Erro ao tentar gerar link de pagamento");
        }
        return listsPagamento;
    }

    private String formatarValor(BigDecimal valor){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormat.format(valor);
    }

}
