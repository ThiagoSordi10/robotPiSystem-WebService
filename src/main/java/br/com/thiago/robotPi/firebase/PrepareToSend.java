package br.com.thiago.robotPi.firebase;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.service.DispositivoService;
import br.com.thiago.robotPi.service.FirebaseService;

@Service
public class PrepareToSend {
	private DispositivoService dispositivoService;
	private FirebaseService firebaseService;
	
	@Autowired
	public PrepareToSend(DispositivoService dispositivoService, FirebaseService firebaseService) {
		this.dispositivoService = dispositivoService;
		this.firebaseService = firebaseService;
	}
	
	public FirebaseSender getFirebaseSender() {
		try {
			return new FirebaseSender(this.firebaseService.getConfig(), this.dispositivoService);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
