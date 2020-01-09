package br.com.gabrielbastos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.gabrielbastos.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

}
