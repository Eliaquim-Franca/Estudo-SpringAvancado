package com.vendas.vendas_jpa_learn.rest.controller;


import com.vendas.vendas_jpa_learn.domain.entity.Produto;
import com.vendas.vendas_jpa_learn.domain.repositoriy.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {

    @Autowired
    private ProdutoRepository repository;



    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto buscaProdutoById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> buscarTodos(){
        return repository.findAll();
    }

    @GetMapping
    public List<Produto> buscarComFiltro(Produto filtro){

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, exampleMatcher);

        return repository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvarProduto(@RequestBody @Valid Produto produto){
        return repository.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto atualizaProduto(@PathVariable Integer id, @Valid @RequestBody Produto produto){
        return repository.findById(id).map(prod -> {
                    produto.setId(prod.getId());
                    repository.save(produto);
                    return prod; // podemos retornar Void.Ty
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id){
        repository.findById(id).map(prod-> {
            repository.delete(prod);
            return prod;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

}
