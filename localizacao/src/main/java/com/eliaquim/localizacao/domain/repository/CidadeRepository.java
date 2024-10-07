package com.eliaquim.localizacao.domain.repository;

import com.eliaquim.localizacao.domain.entity.Cidade;
import com.eliaquim.localizacao.domain.repository.projection.CidadeProjections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {

    //busca pelo nome correto
    List<Cidade> findByNome(String nome);

    //busca pelo nome correto mas temos que colocar % no começo ou final para indicar se queremos que busca começando com a tal letra ou terminando
    //ex: findByNomeLike("a%") aqui eu quero que comece com a
    // Ou findByNomeLike("%za") aqui eu quero que termine com a
    // Sort siginifica ordenado, ou seja, estamos ordenando o resultado que virá
    @Query("select c from Cidade c where upper(c.nome) like upper(?1)")  //'?1' significa que é o primeiro parametro.
    List<Cidade> findByNomeLike(String nome, Sort sort);


    Page<Cidade> findByNomeLike(String nome, Pageable page);// Assim fazemos o método ser Ordenado.


    //busca pelo nome comecando por aquele pedaco
    List<Cidade> findByNomeStartingWith(String nome);


    //busca pelo nome terminando com aquele pedaco
    List<Cidade> findByNomeEndingWith(String nome);


    //busca pelo nome que contem aquele pedaço
    List<Cidade> findByNomeContaining(String nome);

    List<Cidade> findByHabitantes(Long quantidade);

    List<Cidade> findByHabitantesLessThan(Long minima);

    List<Cidade> findByHabitantesLessThanEqual(Long minimaOuIgual);

    List<Cidade> findByHabitantesGreaterThan(Long maxima);

    List<Cidade> findByHabitantesLessThanAndNomeLike(Long minimaOuIgual, String nome);

    // usando o sql, por isso usamos

    @Query(nativeQuery = true, value = "select * from tb_cidade c where c.nome = :nome")
    List<Cidade> findByNomeLikeSqlNativo(@Param("nome") String nome);

    //Quando passamos apenas alguns dados da query (um pedaço dela), usamos as projections
    // o Alias tem que ser igual o nome definido na projection.
    @Query(nativeQuery = true, value = "select c.id_cidade as id, c.nome from tb_cidade c where c.nome = :nome")
    List<CidadeProjections> findByNomeLikeSqlNativoComProjection(@Param("nome") String nome);

}
