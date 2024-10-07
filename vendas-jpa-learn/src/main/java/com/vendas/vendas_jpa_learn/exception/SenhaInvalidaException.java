package com.vendas.vendas_jpa_learn.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha invalida");
    }
}
