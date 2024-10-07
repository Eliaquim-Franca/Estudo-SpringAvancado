package com.vendas.vendas_jpa_learn.rest;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiErrors {
    private List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String mensagem){
        this.errors = Arrays.asList(mensagem);
    }
}
