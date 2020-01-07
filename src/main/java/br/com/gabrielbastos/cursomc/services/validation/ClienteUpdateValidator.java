package br.com.gabrielbastos.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.gabrielbastos.cursomc.domain.Cliente;
import br.com.gabrielbastos.cursomc.dto.ClienteDTO;
import br.com.gabrielbastos.cursomc.repositories.ClienteRepository;
import br.com.gabrielbastos.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ClienteRepository repo;

	@Override
	public boolean isValid(ClienteDTO cliente, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = repo.findByEmail(cliente.getEmail());
		
		if (aux != null && !aux.getId().equals(id)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();

		}

		return list.isEmpty();
	}

}
