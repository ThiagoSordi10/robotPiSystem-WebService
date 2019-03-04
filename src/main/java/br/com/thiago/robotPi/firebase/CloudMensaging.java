package br.com.thiago.robotPi.firebase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CloudMensaging {

	private final Map<String, Object> data = new HashMap<>();
	private String to;

	public Map<String, Object> getData() {
		return Collections.unmodifiableMap(data);
	}

	public void insereChaveEObjeto(String key, Object value) {
		data.put(key, value);
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
