package com.evolucao_spring.vendas;

import com.evolucao_spring.vendas.annontations.Development;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

//@Configuration
//@Profile("development") Apos criamos nossa propria annontation usamos ela
@Development
public class LearnConfiguration {

//    @Bean(name = "applicationName")
//    public String applicationName(){
//        return "Sistema de vendas";
//    }

    @Bean
    public CommandLineRunner executar(){
        return args -> {
            System.out.println("RODANDO  AS CONFIGS DE DEVELOPMENT POIS O PROFILE ESTA USANDO DEVELOPMENT");
        };
    }

}
