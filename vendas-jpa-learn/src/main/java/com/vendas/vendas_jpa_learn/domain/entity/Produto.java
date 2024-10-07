package com.vendas.vendas_jpa_learn.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="descricao")
    @NotEmpty(message = "Campo descricao é obrigatorio")
    private String descricao;

    @Column(name="preco_unitario")
    @NotNull(message = "Campo preço é obrigatorio")
    private BigDecimal preco;
}
