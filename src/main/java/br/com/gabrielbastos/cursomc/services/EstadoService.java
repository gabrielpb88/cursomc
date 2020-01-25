package br.com.gabrielbastos.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielbastos.cursomc.domain.Estado;
import br.com.gabrielbastos.cursomc.dto.EstadoDTO;
import br.com.gabrielbastos.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}

	public Estado fromDTO(EstadoDTO obj) {
		return new Estado(obj.getId(), obj.getNome());
	}

}
