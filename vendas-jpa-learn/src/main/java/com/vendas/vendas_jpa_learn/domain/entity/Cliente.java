package com.vendas.vendas_jpa_learn.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="nome", length = 100)
    @NotEmpty(message = "Campo Nome é obrigatório.")
    private String nome;

    @Column(name="cpf", length = 11)
    @NotEmpty(message = "Informe um CPF")
    @CPF(message = "Informe um cpf valido")
    private String cpf;

    @OneToMany (mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos; //Set não deixa se repetirem os dados

    public Cliente(String nome){
        this.nome = nome;
    };

    public Cliente(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    };
}
