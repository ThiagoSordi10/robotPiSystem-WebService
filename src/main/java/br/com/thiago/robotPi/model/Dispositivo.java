package br.com.thiago.robotPi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Dispositivo implements Cloneable{

	@Id
	private String token;
	@ManyToOne
	private User user;

	public Dispositivo(String token) { //VERIFICAR ESSES PARAMETROS, PASSAR USER
		this.token = token;
	}
	
	public Dispositivo (){
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
    public Dispositivo clone() throws CloneNotSupportedException {
        return (Dispositivo) super.clone();
    }

}
