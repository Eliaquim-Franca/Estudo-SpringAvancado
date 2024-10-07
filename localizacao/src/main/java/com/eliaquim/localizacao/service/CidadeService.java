package com.eliaquim.localizacao.service;

import com.eliaquim.localizacao.domain.entity.Cidade;
import com.eliaquim.localizacao.domain.repository.CidadeRepository;
import com.eliaquim.localizacao.domain.repository.specs.CidadeSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    @Transactional
    public void salvarCidade(){
        var cidade = new Cidade(1L, "São Paulo", 21518955L);
        repository.save(cidade);
    }

    public void listarCidades(){
        repository.findAll().forEach(System.out::println);
    }

    public void listarMinimaOuMaxima(){
        repository.findByHabitantesGreaterThan(10000000L).forEach(System.out::println);
    }

    public void listarMinimaOuMaximaComNome(){
        repository.findByHabitantesLessThanAndNomeLike(1000000L, "%o%").forEach(System.out::println);
    }

    public void listarCidadesPorNomeSql(){
        repository.findByNomeLikeSqlNativo("São Paulo").forEach(System.out::println); // Estamos ordenando pela quantidade de habitantes
    }

    public void listarCidadesPorNomeSqlProjection(){
        repository.findByNomeLikeSqlNativoComProjection("São Paulo")
                .stream()//temos que fazer uma stream e mapear pois o metodo retorna apenas um proxy.
                .map
                        (cidadeProjections -> new Cidade(cidadeProjections.getId(), cidadeProjections.getNome(), null))
                .forEach(System.out::println); // Estamos ordenando pela quantidade de habitantes
    }

    public void listarCidadesPorNome(){
        repository.findByNome("Rio").forEach(System.out::println);
        repository.findByNomeLike("%ro", Sort.by("habitantes")).forEach(System.out::println); // Estamos ordenando pela quantidade de habitantes
    }

    public void listarCidadesPorNomePaginada(){
        Pageable pageable = PageRequest.of(2,2);
        repository.findByNomeLike("%%%%", pageable).forEach(System.out::println); // Estamos ordenando pela quantidade de habitantes
    }

    public List<Cidade> filtroDinamico(Cidade cidade){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        Example<Cidade> example = Example.of(cidade, matcher);
        return repository.findAll(example);
    }

    public void listarCidadesByNomeSpec(){
        Specification<Cidade> nomeEqualSaoPaulo = CidadeSpecs.nomeEqual("São Paulo").and(CidadeSpecs.habitantesGreaterThan(10000L));
        nomeEqualSaoPaulo = CidadeSpecs.nomeEqual("São Paulo").or(CidadeSpecs.habitantesGreaterThan(10000L));
        repository.findAll(nomeEqualSaoPaulo).forEach(System.out::println);
    }

    public void listByProperty(){
        //Aqui passamos o nome da propriedade e o nome que quero comparar
        Specification<Cidade> nomeEqualSaoPaulo = CidadeSpecs.propertyEqual("nome","São Paulo").and(CidadeSpecs.habitantesGreaterThan(10000L));
        repository.findAll(nomeEqualSaoPaulo).forEach(System.out::println);
    }

    public void listarCidadesSpecsFiltroDinamico(Cidade filtro){
        Specification<Cidade> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        //select * from cidade where 1 = 1

        if(filtro.getId() != null){
            specs = specs.and(CidadeSpecs.idEqual(filtro.getId()));
        }

        if(StringUtils.hasText(filtro.getNome())){
            specs = specs.and(CidadeSpecs.nomeEqual(filtro.getNome()));
        }

        if(filtro.getHabitantes() != null){
            specs = specs.and(CidadeSpecs.habitantesGreaterThan(filtro.getHabitantes()));
        }

        repository.findAll(specs).forEach(System.out::println);
    }
}

























