package br.com.thiago.robotPi.dto;


import java.util.List;

import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Dispositivo;


public class DispositivoSync {

	private final List<Dispositivo> dispositivos;
	private Dispositivo dispositivo;

	public DispositivoSync(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public DispositivoSync(Dispositivo dispositivo) {
		this.dispositivos = null;
		this.dispositivo = dispositivo;
	}

	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}
	
	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

}
