package br.com.thiago.robotPi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class User {

	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	private String id;
	private String nome;
	private String senha;
	@ManyToOne
	private Empresa empresa;
	private String telefone;
	private String email;
	private int desativado;

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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDesativado() {
		return desativado;
	}

	public void setDesativado(int desativado) {
		this.desativado = desativado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		User aluno = (User) obj;
		if (aluno.id == this.id)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", senha=" + senha + 
				", telefone=" + telefone + ", empresa=" + empresa + ", email=" + email + 
				", desativado=" + desativado
				+ "]";
	}


}
