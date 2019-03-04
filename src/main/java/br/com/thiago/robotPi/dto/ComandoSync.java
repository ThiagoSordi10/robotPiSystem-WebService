package br.com.thiago.robotPi.dto;


import java.util.List;

import br.com.thiago.robotPi.model.Comando;


public class ComandoSync {

	private final List<Comando> comandos;
	private Comando comando;

	public ComandoSync(List<Comando> comandos) {
		this.comandos = comandos;
	}

	public ComandoSync(Comando comando) {
		this.comandos = null;
		this.comando = comando;
	}

	public List<Comando> getComandos() {
		return comandos;
	}

	public Comando getComando() {
		return comando;
	}
	
	public void setComando(Comando comando) {
		this.comando = comando;
	}

}
