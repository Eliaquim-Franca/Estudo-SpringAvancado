package com.evolucao_spring.vendas.controller;

import com.evolucao_spring.vendas.annontations.Cachorro;
import com.evolucao_spring.vendas.annontations.Gato;
import com.evolucao_spring.vendas.interfaces.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testes")
public class VendasController {

//    @Autowired
//    @Qualifier("applicationName")
//    private String app;

    @Cachorro
    //@Gato
    private Animal animal;

    @Bean
    public CommandLineRunner executarAnimal(){
        return  args -> {
            this.animal.fazerBarulho();
        };
    }

    @Autowired
    @Value("${application.name}")
    private String applicationNameFromProperties;

    @GetMapping("/hello")
    public String sayHello(){
        return  "Essa veio do properties ->" + applicationNameFromProperties;
    }

}
