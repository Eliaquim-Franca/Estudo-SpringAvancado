package com.vendas.vendas_jpa_learn.service;

import com.vendas.vendas_jpa_learn.domain.entity.Pedido;
import com.vendas.vendas_jpa_learn.domain.enums.StatusPedido;
import com.vendas.vendas_jpa_learn.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvarPedido(PedidoDTO pedidoDTO);

   Optional<Pedido> obterPedidoCompleto(Integer id);

   void atualizaStatusPedido(Integer id, StatusPedido statusPedido );
}
