package br.com.thiago.robotPi.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDUtils {

	public String UUIDGenerator() {
		return UUID.randomUUID().toString();
	}

	public boolean validaUUID(String uuid) {
		return uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
	}

}
