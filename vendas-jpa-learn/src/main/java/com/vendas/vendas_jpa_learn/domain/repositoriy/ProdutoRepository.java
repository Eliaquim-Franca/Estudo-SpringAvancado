package com.vendas.vendas_jpa_learn.domain.repositoriy;

import com.vendas.vendas_jpa_learn.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
