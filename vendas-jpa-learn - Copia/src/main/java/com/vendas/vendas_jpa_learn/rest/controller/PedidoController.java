package com.vendas.vendas_jpa_learn.rest.controller;

import com.vendas.vendas_jpa_learn.domain.entity.ItemPedido;
import com.vendas.vendas_jpa_learn.domain.entity.Pedido;
import com.vendas.vendas_jpa_learn.domain.enums.StatusPedido;
import com.vendas.vendas_jpa_learn.rest.dto.AtualizacaoSatusPedidoDTO;
import com.vendas.vendas_jpa_learn.rest.dto.InformacaoItemPedidoDTO;
import com.vendas.vendas_jpa_learn.rest.dto.InformacoesPedidoDTO;
import com.vendas.vendas_jpa_learn.rest.dto.PedidoDTO;
import com.vendas.vendas_jpa_learn.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Integer salvarPedido(@RequestBody @Valid  PedidoDTO pedidoDTO){
            Pedido pedido = service.salvarPedido(pedidoDTO);
            return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service.obterPedidoCompleto(id)
                .map(this::converter)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody  AtualizacaoSatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatusPedido(id, StatusPedido.valueOf(novoStatus));
    }



    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converterItems(pedido.getItens())).build();

    }

    private List<InformacaoItemPedidoDTO> converterItems(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
                        item-> InformacaoItemPedidoDTO.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade()).build()
                ).collect(Collectors.toList());
    }
}
