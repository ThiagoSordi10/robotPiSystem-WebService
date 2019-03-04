package br.com.thiago.robotPi.dto;


import java.util.List;

import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.model.Raspberry;


public class MensagemSync {

	private final List<Mensagem> mensagens;
	private Mensagem mensagem;

	public MensagemSync(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public MensagemSync(Mensagem mensagem) {
		this.mensagens = null;
		this.mensagem = mensagem;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

}
