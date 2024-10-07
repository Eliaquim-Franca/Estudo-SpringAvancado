package com.vendas.vendas_jpa_learn.rest.controller;

import com.vendas.vendas_jpa_learn.exception.exception.PedidoNaoEncontradoException;
import com.vendas.vendas_jpa_learn.exception.exception.RegraNegocioException;
import com.vendas.vendas_jpa_learn.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice

public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException excecao){
            String mensagemErro = excecao.getMessage();
            return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return  new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException met){
        List<String> erros = met.getBindingResult().getAllErrors()
                .stream()
                .map( erro-> erro.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(erros);

    }
}
