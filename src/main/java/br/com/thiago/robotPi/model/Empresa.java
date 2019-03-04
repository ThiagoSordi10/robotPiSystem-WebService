package br.com.thiago.robotPi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Empresa {
	
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	private String id;
	private String nome;
	private String cep;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
}
