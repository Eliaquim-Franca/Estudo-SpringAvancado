package com.vendas.vendas_jpa_learn.exception.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException() {
        super("Pedido Não encontrado");
    }
}
