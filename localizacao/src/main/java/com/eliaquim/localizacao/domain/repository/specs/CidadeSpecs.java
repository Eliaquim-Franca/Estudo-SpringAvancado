package com.eliaquim.localizacao.domain.repository.specs;

import com.eliaquim.localizacao.domain.entity.Cidade;
import org.springframework.data.jpa.domain.Specification;

public abstract class CidadeSpecs {

    public static Specification<Cidade> propertyEqual(String prop, Object value){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("prop"), value);
    }

    public static Specification<Cidade> nomeEqual(String nome){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nome"), nome);
    }

    public static Specification<Cidade> idEqual(Long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Cidade> habitantesGreaterThan(Long maior){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("habitantes"), maior);
    }

    public static Specification<Cidade> habitantesBetween(Long max, Long min){
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("habitantes"), max,min);
    }

    public static Specification<Cidade> nomeLike(String nome){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), nome);
    }

    public static Specification<Cidade> nomeLikeUpperCase(String nome){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%"+nome+"%".toUpperCase());
    }
}
