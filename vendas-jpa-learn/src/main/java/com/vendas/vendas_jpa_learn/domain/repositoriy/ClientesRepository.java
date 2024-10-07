package com.vendas.vendas_jpa_learn.domain.repositoriy;

import com.vendas.vendas_jpa_learn.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientesRepository  extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);

    @Query(value = "select c from Cliente c where c.nome like :nome") // esse tipo de query é o tipo hql
    List<Cliente> encontrarPorNomeExemploEmPortuguesHQL(@Param("nome") String nome);

    @Query(value = "select c from cliente c where c.nome like '%:nome%' ", nativeQuery = true) // esse tipo de query é o sql Nativo
    List<Cliente> encontrarPorNomeExemploEmPortuguesSQL(@Param("nome") String nome);

    @Modifying
    void  deleteByNome(String nome);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

    
}
