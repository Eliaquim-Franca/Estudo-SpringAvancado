package com.vendas.vendas_jpa_learn.rest.dto;

import com.vendas.vendas_jpa_learn.domain.entity.Cliente;
import com.vendas.vendas_jpa_learn.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoDTO {

    @NotNull(message = "Informe o codigo do cliente.")
    private Integer clienteID;
    @NotNull(message = "Campo total do pedido é obrigatorio.")
    private BigDecimal total;
    @NotEmptyList(message = "O pedido não pode ser realizado sem itens.")
    private List<ItemPedidoDTO> items;

}
