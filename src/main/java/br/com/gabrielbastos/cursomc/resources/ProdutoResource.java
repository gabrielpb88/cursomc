package br.com.gabrielbastos.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielbastos.cursomc.domain.Produto;
import br.com.gabrielbastos.cursomc.dto.ProdutoDTO;
import br.com.gabrielbastos.cursomc.resources.utils.URL;
import br.com.gabrielbastos.cursomc.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		return ResponseEntity.ok(service.find(id));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findAllByPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias, 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String directions) {
		
		Page<Produto> lista = service.search(URL.decodeParam(nome), URL.decodeInList(categorias), page, linesPerPage, orderBy, directions);
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listaDTO);
	}
}
