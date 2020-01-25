package br.com.gabrielbastos.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrielbastos.cursomc.domain.Cliente;
import br.com.gabrielbastos.cursomc.domain.Endereco;
import br.com.gabrielbastos.cursomc.domain.enums.Perfil;
import br.com.gabrielbastos.cursomc.domain.enums.TipoCliente;
import br.com.gabrielbastos.cursomc.dto.ClienteDTO;
import br.com.gabrielbastos.cursomc.dto.ClienteNewDTO;
import br.com.gabrielbastos.cursomc.repositories.CidadeRepository;
import br.com.gabrielbastos.cursomc.repositories.ClienteRepository;
import br.com.gabrielbastos.cursomc.repositories.EnderecoRepository;
import br.com.gabrielbastos.cursomc.security.UserSS;
import br.com.gabrielbastos.cursomc.services.exception.AuthorizationException;
import br.com.gabrielbastos.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository repoEndereco;

	@Autowired
	private CidadeRepository repoCidade;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Cliente find(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado!");
		}

		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);

		repoEndereco.saveAll(obj.getEnderecos());

		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente cliente = find(obj.getId());
		updateData(cliente, obj);
		return repo.save(cliente);
	}

	private void updateData(Cliente cliente, Cliente obj) {
		cliente.setNome(obj.getNome());
		cliente.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId());
		}
		return cli;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String directions) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(directions), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null, null);
	}

	public Cliente fromDTO(@Valid ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipoCliente()), bCryptPasswordEncoder.encode(objDTO.getSenha()));

		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, repoCidade.findById(objDTO.getCidadeId()).get());
		cli.getEnderecos().add(end);

		cli.getTelefones().add(objDTO.getTelefone1());
		cli.getTelefones().add((objDTO.getTelefone2() != null) ? objDTO.getTelefone2() : null);
		cli.getTelefones().add((objDTO.getTelefone3() != null) ? objDTO.getTelefone3() : null);

		return cli;
	}

}
