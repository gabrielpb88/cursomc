package br.com.gabrielbastos.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.gabrielbastos.cursomc.domain.enums.TipoCliente;
import br.com.gabrielbastos.cursomc.dto.ClienteNewDTO;
import br.com.gabrielbastos.cursomc.resources.exception.FieldMessage;
import br.com.gabrielbastos.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{
	
	@Override
	public boolean isValid(ClienteNewDTO cliente, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(cliente.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(cliente.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(cliente.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(cliente.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName())
			.addConstraintViolation();
			
		}
		
		return list.isEmpty();
	}

	
}
