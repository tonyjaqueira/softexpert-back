package com.softexpertpayments.domain.service;

import com.softexpertpayments.domain.model.Pedido;
import com.softexpertpayments.domain.model.Rateio;

public interface CalculaPedidoService {
    Rateio calcularRateio(Pedido pedido);
}
