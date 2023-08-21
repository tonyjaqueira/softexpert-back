package com.softexpertpayments.service;

import com.softexpertpayments.domain.exception.ExceptionGeneralCustomer;
import com.softexpertpayments.domain.model.Item;
import com.softexpertpayments.domain.model.Pagamento;
import com.softexpertpayments.domain.model.Pedido;
import com.softexpertpayments.domain.service.CalculaPedidoServiceImpl;
import com.softexpertpayments.domain.service.PagamentoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class CalculaPedidoServiceTeste {

    @InjectMocks
    CalculaPedidoServiceImpl calculaPedidoService;

    @Mock
    PagamentoServiceImpl pagamentoService;



    @Test
    void deveCalcularRateioDePedidoAgrupadoPorCliente(){
        when(pagamentoService.criarLinkPagamentoAssas(any())).thenReturn(getPagamento());
        var pedido = getPedido();
        var response = calculaPedidoService.calcularRateio(pedido);

        assertEquals(new BigDecimal(38), response.valorTotalPedido());
        assertEquals(2, response.valorPorPessoa().size());
        var cliente1 = response.valorPorPessoa().get("Tony");
        var cliente2 = response.valorPorPessoa().get("Amigo");
        assertEquals(new BigDecimal("31.92"), cliente1.setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("6.08"), cliente2.setScale(2, RoundingMode.HALF_UP));
    }


    @Test
    void deveGerarExcecaoDeImdisponibilidadeDeLinksPagamento(){
        when(pagamentoService.criarLinkPagamentoAssas(any())).thenReturn(Collections.emptyList());
        var pedido = getPedido();
        var exception = assertThrows(ExceptionGeneralCustomer.class, () -> calculaPedidoService.calcularRateio(pedido));
        assertEquals("Ocorreu um erro ao gerar links de pagamento!", exception.getMessage());
    }

    private Pedido getPedido() {
        return Pedido.builder()
                .valorEntrega(new BigDecimal(8))
                .valorDesconto(new BigDecimal(20))
                .itensPedido(getItermsPedido()).build();
    }

    private List<Item> getItermsPedido(){
        var list = new ArrayList<Item>();
        var item1 = Item.builder()
                .valorItem(new BigDecimal(40))
                .cliente("Tony")
                .descricao("Hamburguer").build();
        var item2 = Item.builder()
                .valorItem(new BigDecimal(2))
                .cliente("Tony")
                .descricao("Sobremesa").build();
        var item3 = Item.builder()
                .valorItem(new BigDecimal(8))
                .cliente("Amigo")
                .descricao("Sanduíche ").build();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        return list;
    }

    private List<Pagamento> getPagamento(){
        var pag1 = Pagamento.builder()
                .link("https://sandbox.asaas.com/c/922806507294")
                .valor("R$ 2,00").build();
        return List.of(pag1);
    }

}
