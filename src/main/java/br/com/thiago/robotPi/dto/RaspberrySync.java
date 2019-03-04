package br.com.thiago.robotPi.dto;


import java.util.List;

import br.com.thiago.robotPi.model.Raspberry;


public class RaspberrySync {

	private final List<Raspberry> raspberries;
	private Raspberry raspberry;

	public RaspberrySync(List<Raspberry> raspberries) {
		this.raspberries = raspberries;
	}

	public RaspberrySync(Raspberry raspberry) {
		this.raspberries = null;
		this.raspberry = raspberry;
	}

	public List<Raspberry> getRaspberries() {
		return raspberries;
	}

	public Raspberry getRaspberry() {
		return raspberry;
	}
	
	public void setRaspberry(Raspberry raspberry) {
		this.raspberry = raspberry;
	}

}
