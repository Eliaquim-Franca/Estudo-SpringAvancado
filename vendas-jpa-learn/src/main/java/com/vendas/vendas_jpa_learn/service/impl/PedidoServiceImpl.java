package com.vendas.vendas_jpa_learn.service.impl;

import com.vendas.vendas_jpa_learn.domain.entity.Cliente;
import com.vendas.vendas_jpa_learn.domain.entity.ItemPedido;
import com.vendas.vendas_jpa_learn.domain.entity.Pedido;
import com.vendas.vendas_jpa_learn.domain.entity.Produto;
import com.vendas.vendas_jpa_learn.domain.enums.StatusPedido;
import com.vendas.vendas_jpa_learn.exception.exception.PedidoNaoEncontradoException;
import com.vendas.vendas_jpa_learn.exception.exception.RegraNegocioException;
import com.vendas.vendas_jpa_learn.domain.repositoriy.ClientesRepository;
import com.vendas.vendas_jpa_learn.domain.repositoriy.ItemsPedidoRepository;
import com.vendas.vendas_jpa_learn.domain.repositoriy.PedidoRepository;
import com.vendas.vendas_jpa_learn.domain.repositoriy.ProdutoRepository;
import com.vendas.vendas_jpa_learn.rest.dto.ItemPedidoDTO;
import com.vendas.vendas_jpa_learn.rest.dto.PedidoDTO;
import com.vendas.vendas_jpa_learn.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {


    private final PedidoRepository pedidoRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemsPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvarPedido(PedidoDTO pedidoDTO) {

        Integer clienteId = pedidoDTO.getClienteID();
        Cliente cliente = clientesRepository.findById(clienteId).orElseThrow(()-> new RegraNegocioException("Id do cliente inexistente"));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

       List<ItemPedido> itemPedidos = converterItems(pedido, pedidoDTO.getItems());

       pedidoRepository.save(pedido);
       itemsPedidoRepository.saveAll(itemPedidos);
       pedido.setItens(itemPedidos);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatusPedido(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedido);
            return pedidoRepository.save(pedido);
        }).orElseThrow(()-> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items");
        }

        return items.stream().map(dto ->{

            Integer idProduto = dto.getProduto();
           Produto prod = produtoRepository.findById(idProduto).orElseThrow(()-> new RegraNegocioException("Código de Produto Invalido"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(prod);

            return  itemPedido;
        }).collect(Collectors.toList());
    }
}
