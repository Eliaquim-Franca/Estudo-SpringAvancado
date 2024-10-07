package com.eliaquim.localizacao;

import com.eliaquim.localizacao.domain.entity.Cidade;
import com.eliaquim.localizacao.domain.repository.CidadeRepository;
import com.eliaquim.localizacao.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class LocalizacaoApplication implements CommandLineRunner {

	@Autowired
	private CidadeService service;

	@Override
	public void run(String... args) throws Exception {

		var cidade = new Cidade(null, "rio", null);
		service.filtroDinamico(cidade).forEach(System.out::println);

	}

	public static void main(String[] args) {
		SpringApplication.run(LocalizacaoApplication.class, args);
	}

}
