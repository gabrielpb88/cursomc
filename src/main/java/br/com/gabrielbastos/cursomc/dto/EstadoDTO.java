package br.com.gabrielbastos.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.gabrielbastos.cursomc.domain.Estado;

public class EstadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;

	public EstadoDTO() {
	}

	public EstadoDTO(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public EstadoDTO(Estado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
