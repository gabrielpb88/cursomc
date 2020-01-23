package br.com.gabrielbastos.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gabrielbastos.cursomc.domain.Cliente;
import br.com.gabrielbastos.cursomc.repositories.ClienteRepository;
import br.com.gabrielbastos.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email){
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		String newPassword = newPassword();
		cliente.setSenha(pe.encode(newPassword));
		
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}
	
	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10;i++) {
			vet[i] = randomChar();
		}
		return vet.toString();
	}
	
	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) {
			return (char) (random.nextInt(10)+48);
		}
		else if(opt == 1) {
			return (char) (random.nextInt(26)+65);
		}
		else {
			return (char) (random.nextInt(26)+97);
		}
	}
}
