package br.com.thiago.robotPi.service;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.firebase.FirebaseClient;
import br.com.thiago.robotPi.firebase.FirebaseConfig;
import br.com.thiago.robotPi.repository.FirebaseConfigRepository;

@Service
public class FirebaseService {

	private FirebaseConfigRepository firebaseRepos;

	@Inject
	public FirebaseService(FirebaseConfigRepository firebaseRepos) {
		this.firebaseRepos = firebaseRepos;
	}

	public boolean tokenValido() throws IOException {
		return new FirebaseClient(getConfig()).validatorAPIKey();
	}

	public void salva(FirebaseConfig config) {
		config.setId(1L);
		firebaseRepos.save(config);
	}

	public FirebaseConfig getConfig() {
		FirebaseConfig config = firebaseRepos.findOne(1L);
		if (config == null) {
			return new FirebaseConfig("https://fcm.googleapis.com/fcm/send", "");
		}
		return config;
	}
}
