package br.com.gabrielbastos.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielbastos.cursomc.domain.Cidade;
import br.com.gabrielbastos.cursomc.domain.Estado;
import br.com.gabrielbastos.cursomc.dto.CidadeDTO;
import br.com.gabrielbastos.cursomc.dto.EstadoDTO;
import br.com.gabrielbastos.cursomc.services.CidadeService;
import br.com.gabrielbastos.cursomc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> lista = service.findAll();
		List<EstadoDTO> listaDTO = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findByEstado(@PathVariable("estadoId") Integer estadoId) {
		List<Cidade> lista = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listaDTO = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
}
