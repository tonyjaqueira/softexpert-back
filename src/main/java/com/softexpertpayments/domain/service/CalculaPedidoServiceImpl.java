package com.softexpertpayments.domain.service;

import com.softexpertpayments.domain.exception.ExceptionGeneralCustomer;
import com.softexpertpayments.domain.model.Item;
import com.softexpertpayments.domain.model.Pagamento;
import com.softexpertpayments.domain.model.Pedido;
import com.softexpertpayments.domain.model.Rateio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalculaPedidoServiceImpl implements CalculaPedidoService{

    private final PagamentoServiceImpl pagamentoService;

    public CalculaPedidoServiceImpl(PagamentoServiceImpl pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    public Rateio calcularRateio(Pedido pedido){
        log.info("### Cálculo do rateio iniciado ###");
        var valorTotalPedido = calcularValorTotalPedido(pedido.itensPedido());
        var valorAPagar = calcularValorTotalPagar(valorTotalPedido, pedido.valorEntrega(), pedido.valorDesconto());
        var valorPorPessoa = valorPagarPorPessoa(pedido, valorTotalPedido, valorAPagar);
        Rateio rateio = Rateio.builder()
                .valorTotalPedido(valorAPagar)
                .valorPorPessoa(valorPorPessoa)
                .linksPagamento(gerarLinksPagamento(valorPorPessoa))
                .build();
        log.info("### Cálculo do rateio finalizado ###");
        return rateio;
    }

    private List<Pagamento> gerarLinksPagamento(Map<String, BigDecimal> valorPorPessoa) {
        var linksPagamewnto = pagamentoService.criarLinkPagamentoAssas(valorPorPessoa);
        if(linksPagamewnto.isEmpty()){
            throw new ExceptionGeneralCustomer("Ocorreu um erro ao gerar links de pagamento!");
        }
        return linksPagamewnto;
    }

    private Map<String, BigDecimal> valorPagarPorPessoa(Pedido pedido, BigDecimal valorTotalPedido, BigDecimal valorAPagar){
        var valorPorPessoa = new HashMap<String, BigDecimal>();
        var itensAgrupadoPorPessoa = itensPorPessoa(pedido);
        itensAgrupadoPorPessoa.entrySet().forEach(itemAgrupado -> {
            var percentual = calcularPercentualPessoa(itemAgrupado.getValue(), valorTotalPedido);
            var valorPagarPorPessoa = percentual.divide(BigDecimal.valueOf(100)).multiply(valorAPagar);
            valorPorPessoa.put(itemAgrupado.getKey(), valorPagarPorPessoa);
        });
        return valorPorPessoa;
    }

    private Map<String, BigDecimal> itensPorPessoa(Pedido pedido){
        return pedido.itensPedido().stream()
                .collect(Collectors.groupingBy(Item::cliente, Collectors.reducing(BigDecimal.ZERO, Item::valorItem, BigDecimal::add)));
    }

    private BigDecimal calcularPercentualPessoa(BigDecimal valorPessoa, BigDecimal valorTotalItens){
        return valorPessoa.multiply(BigDecimal.valueOf(100)).divide(valorTotalItens, 1, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularValorTotalPedido(List<Item> listaItens) {
        return listaItens.stream().map(Item::valorItem).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularValorTotalPagar(BigDecimal valorTotalItens, BigDecimal valorTaxaEntrega, BigDecimal valorDesconto) {
        return BigDecimal.ZERO.add(valorTotalItens).add(valorTaxaEntrega).subtract(valorDesconto);
    }

}
