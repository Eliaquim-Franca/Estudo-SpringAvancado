package com.vendas.vendas_jpa_learn.rest.controller;

import com.vendas.vendas_jpa_learn.domain.entity.Cliente;
import com.vendas.vendas_jpa_learn.domain.repositoriy.ClientesRepository;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Api("API DE CLIENTES")
public class ClienteController {

    @Autowired
    private ClientesRepository repository;

    @RequestMapping(
            value = {"/hello/primeiraUrl/{nome}", "/hello/segundaUrl/{nome}"},
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"}, // no caso se fosse um metodo POST ele consumiria json ou xml
            produces = {"application/json", "application/xml"}// no caso se fosse um metodo POST ele consumiria json ou xml
    )

    public String ExemplohelloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente){
        return String.format("Hello %s", nomeCliente );
    }

    @GetMapping("{id}")
    @ApiOperation("Pegar os clientes por id")
    @ApiResponses({
            @ApiResponse(code= 200, message = "Cliente encontrado"),
            @ApiResponse(code= 404, message = "Cliente não encontrado para o ID informado.")
    })
    public Cliente getClienteById(@PathVariable("id") @ApiParam("Id do cliente") Integer id){
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException( HttpStatus.NOT_FOUND ,"Cliente não encontrado"));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvarCliente(@RequestBody @Valid Cliente cliente){
        return repository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        repository.findById(id).map( cliente -> {
            repository.delete(cliente);
            return  cliente; })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));


    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualiza(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){

        repository.findById(id).map(  clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            repository.save(cliente);
            return clienteExistente;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @GetMapping
    public  List<Cliente> buscarCliente(Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);

       return repository.findAll(example);
    }


//    @GetMapping("{id}")
//    public ResponseEntity<Cliente> AntigoModogetClienteById(@PathVariable("id") Integer id){
//         Optional<Cliente> cliente = repository.findById(id);
//
//         if(cliente.isPresent()){
//           return  ResponseEntity.ok(cliente.get());
//         }
//
//         return ResponseEntity.notFound().build();
//    }


//    @PostMapping
//    public ResponseEntity AntigoModosalvarCliente(@RequestBody  Cliente cliente){
//        Cliente clienteSalvo = repository.save(cliente);
//        return ResponseEntity.ok(clienteSalvo);
//    }
//
//    @DeleteMapping("{id}")
//    public  ResponseEntity AntigoMododelete(@PathVariable Integer id){
//         Optional<Cliente> clienteParaDeletar = repository.findById(id);
//
//         if(clienteParaDeletar.isPresent()){
//             repository.delete(clienteParaDeletar.get());
//             return  ResponseEntity.noContent().build();
//         }
//
//         return ResponseEntity.notFound().build();
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity AntigoModoatualiza(@PathVariable Integer id, @RequestBody Cliente cliente){
//
//        return  repository.findById(id).map(  clienteExistente -> {
//            cliente.setId(clienteExistente.getId());
//            repository.save(cliente);
//            return ResponseEntity.noContent().build();
//        }).orElseGet( () -> ResponseEntity.notFound().build());
//
//    }
//
//    @GetMapping
//    public  ResponseEntity AntigoModobuscarCliente(Cliente filtro){
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        Example<Cliente> example = Example.of(filtro, matcher);
//
//        List<Cliente> lista = repository.findAll(example);
//        return ResponseEntity.ok(lista);
//
//    }

}
