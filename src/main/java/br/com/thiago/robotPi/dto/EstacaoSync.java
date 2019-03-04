package br.com.thiago.robotPi.dto;


import java.util.List;

import br.com.thiago.robotPi.model.Estacao;

public class EstacaoSync {

	private final List<Estacao> estacoes;
	private Estacao estacao;

	public EstacaoSync(List<Estacao> estacoes) {
		this.estacoes = estacoes;
	}

	public EstacaoSync(Estacao estacao) {
		this.estacoes = null;
		this.estacao = estacao;
	}

	public List<Estacao> getEstacoes() {
		return estacoes;
	}

	public Estacao getEstacao() {
		return estacao;
	}
	
	public void setEstacao(Estacao estacao) {
		this.estacao = estacao;
	}

}
