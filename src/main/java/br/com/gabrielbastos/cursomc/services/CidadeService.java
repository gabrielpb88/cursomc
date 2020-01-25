package br.com.gabrielbastos.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielbastos.cursomc.domain.Cidade;
import br.com.gabrielbastos.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer id){
		return repo.findCidades(id);
	}

}
