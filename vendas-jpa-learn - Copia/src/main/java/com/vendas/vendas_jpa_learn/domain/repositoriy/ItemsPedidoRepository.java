package com.vendas.vendas_jpa_learn.domain.repositoriy;

import com.vendas.vendas_jpa_learn.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
