package br.com.gabrielbastos.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.gabrielbastos.cursomc.domain.Pedido;

public abstract class AbstractMailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage msg = prepareSimpleMailMessage(obj);
		sendEmail(msg);

	}

	protected SimpleMailMessage prepareSimpleMailMessage(Pedido obj) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(obj.getCliente().getEmail());
		msg.setFrom(sender);
		msg.setSubject("Pedido confirmado: " + obj.getId());
		msg.setSentDate(new Date(System.currentTimeMillis()));
		msg.setText(obj.toString());
		return msg;
	}

}
