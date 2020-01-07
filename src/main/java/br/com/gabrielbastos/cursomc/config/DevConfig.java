package br.com.gabrielbastos.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.gabrielbastos.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	public String chave;
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDB() throws ParseException {
		if("create".equals(chave)) {
			dbService.instantiateTestDatabase();
		}
		return true;
	}
}
