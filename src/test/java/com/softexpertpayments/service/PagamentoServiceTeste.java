package com.softexpertpayments.service;

import com.softexpertpayments.client.AssasClient;
import com.softexpertpayments.domain.model.Pagamento;
import com.softexpertpayments.domain.model.response.PaymentLinksResponse;
import com.softexpertpayments.domain.service.PagamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PagamentoServiceTeste {

    PagamentoServiceImpl pagamentoService;

    @Mock
    AssasClient assasClient;

    @BeforeEach
    void setUp(){
        pagamentoService = new PagamentoServiceImpl(assasClient, "1235454");
    }


    @Test
    void deveRetornarLinksDePagamentoSaass(){
        when(assasClient.gerarLinkPagamento(any(),anyString())).thenReturn(getPaymentLinksResponse());
        var listaPagamentos = pagamentoService.criarLinkPagamentoAssas(getListaClientes());
        assertEquals(2, listaPagamentos.size());
    }

    @Test
    void deveGerarUmaExecaoAoGearLinksPagamento(){
        pagamentoService = new PagamentoServiceImpl(assasClient, null);
        var listaPagamentos = pagamentoService.criarLinkPagamentoAssas(getListaClientes());
        assertTrue(listaPagamentos.isEmpty());
    }


    private PaymentLinksResponse getPaymentLinksResponse(){
        return PaymentLinksResponse.builder()
                .id("922806507294")
                .url("https://sandbox.asaas.com/c/922806507294").build();
    }

    private Map<String, BigDecimal> getListaClientes(){
        Map<String, BigDecimal> lista = new HashMap<>();
        lista.put("Tony", new BigDecimal(20));
        lista.put("Amigo", new BigDecimal(8));
        lista.put("Tony", new BigDecimal(5));
        return lista;
    }

}
