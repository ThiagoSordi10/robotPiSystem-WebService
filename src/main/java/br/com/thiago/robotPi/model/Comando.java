package br.com.thiago.robotPi.model;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.thiago.robotPi.converter.DateConverter;

@Entity
public class Comando {
	
	@Id
	@GenericGenerator(name = "id", strategy = "uuid2")
	private String id;
	@ManyToOne
	private Estacao estacaoSaida;
	private int executado; // 0-not 1-almost 2-doing 3-done
	@ManyToOne
	private Estacao estacaoChegada;
	@ManyToOne
	private User user;
	@ManyToOne
	private Raspberry raspberry;
	@Convert(converter = DateConverter.class)
	private Date horarioSaida;
	@Convert(converter = DateConverter.class)
	private Date horarioChegada;
	
	public Raspberry getRaspberry() {
		return raspberry;
	}
	public void setRaspberry(Raspberry raspberry) {
		this.raspberry = raspberry;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Estacao getEstacaoSaida() {
		return estacaoSaida;
	}
	public void setEstacaoSaida(Estacao estacaoSaida) {
		this.estacaoSaida = estacaoSaida;
	}
	public Estacao getEstacaoChegada() {
		return estacaoChegada;
	}
	public void setEstacaoChegada(Estacao estacaoChegada) {
		this.estacaoChegada = estacaoChegada;
	}
	public Date getHorarioSaida() {
		return horarioSaida;
	}
	public void setHorarioSaida(Date horarioSaida) {
		this.horarioSaida = horarioSaida;
	}
	public Date getHorarioChegada() {
		return horarioChegada;
	}
	public void setHorarioChegada(Date horarioChegada) {
		this.horarioChegada = horarioChegada;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getExecutado() {
		return executado;
	}
	public void setExecutado(int executado) {
		this.executado = executado;
	}
	
	
}
