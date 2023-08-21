package com.softexpertpayments.domain.service;

import com.softexpertpayments.domain.model.Pagamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PagamentoService {
    List<Pagamento> criarLinkPagamentoAssas(Map<String, BigDecimal> lista);
}
