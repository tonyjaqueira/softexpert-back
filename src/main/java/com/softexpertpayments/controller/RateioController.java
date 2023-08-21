package com.softexpertpayments.controller;

import com.softexpertpayments.domain.model.Pedido;
import com.softexpertpayments.domain.model.Rateio;
import com.softexpertpayments.domain.service.CalculaPedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/softexperts")
@AllArgsConstructor
@CrossOrigin
@Tag(name = "RateioController", description = "Serviço para rateio de contas.")
public class RateioController {

    private final CalculaPedidoService calculaPedidoService;

    @PostMapping("/calcular-rateio")
    public ResponseEntity<Rateio>  calcularRateio(@RequestBody Pedido pedido){
        var rateio = calculaPedidoService.calcularRateio(pedido);
        return ResponseEntity.ok(rateio);
    }

}
