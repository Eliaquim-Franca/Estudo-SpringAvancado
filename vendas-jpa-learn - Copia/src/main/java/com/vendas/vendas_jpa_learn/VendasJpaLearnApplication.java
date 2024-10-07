package com.vendas.vendas_jpa_learn;

import com.vendas.vendas_jpa_learn.domain.entity.Cliente;
import com.vendas.vendas_jpa_learn.domain.repositoriy.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class VendasJpaLearnApplication {

//	@Bean
//	public CommandLineRunner init(@Autowired ClientesRepository clientesRepository){
//		return  args -> {
//
//			System.out.println("SALVANDO CLIENTES");
//			clientesRepository.save(new Cliente("Silas"));
//			clientesRepository.save(new Cliente("Eliaquim"));
//
//			List<Cliente> todosClientes= clientesRepository.findAll();
//			todosClientes.forEach(System.out::println);
//
//			System.out.println("ATUALIZANDO CLIENTE");
//			todosClientes.forEach(c -> {
//				c.setNome(c.getNome() + "Atualizado.");
//				clientesRepository.save(c);
//			});
//
//			System.out.println("BUSCANDO CLIENTE");
//			clientesRepository.findByNomeLike("eli").forEach(System.out::println);
//
//
//			System.out.println("DELETANDO CLIENTES");
//			clientesRepository.findAll().forEach(c->{
//				clientesRepository.delete(c);
//			});
//
//			todosClientes= clientesRepository.findAll();
//			if(todosClientes.isEmpty()){
//				System.out.println("Nenhum Cliente Encontrado.");
//			}else{
//				todosClientes.forEach(System.out::println);
//			}
//
//
//
//		};
//	}

//	@Bean
//	public CommandLineRunner commandLineRunner(@Autowired ClientesRepository repository){
//		return args -> {
//			Cliente c = new Cliente(null, "Silas Eliaquim");
//			repository.save(c);
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(VendasJpaLearnApplication.class, args);
	}

}
