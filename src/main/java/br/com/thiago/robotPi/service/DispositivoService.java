package br.com.thiago.robotPi.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.dto.UserSync;
import br.com.thiago.robotPi.firebase.FirebaseSender;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.repository.DispositivoRepository;
import br.com.thiago.robotPi.repository.UserRepository;

@Service
public class DispositivoService {

	private final DispositivoRepository dispositivoRepository;
	private static final Logger LOGGER = Logger.getLogger(DispositivoService.class);
	private FirebaseService firebaseService;
	private UserRepository userRepository;

	@Autowired
	public DispositivoService(UserRepository userRepository, DispositivoRepository dispositivoRepository, FirebaseService firebaseService) {
		this.dispositivoRepository = dispositivoRepository;
		this.firebaseService = firebaseService;
		this.userRepository = userRepository;
	}

	public void salva(Dispositivo dispositivo) {
		//User user = userRepository.findOne(userID);
		//dispositivo.setUser(user);
		dispositivoRepository.save(dispositivo);
		LOGGER.info("dispositivo salvo " + dispositivo.getToken());		
	}

	@Async
	public void enviaNotificacao(User user) {
		//List<Dispositivo> dispositivos = (List<Dispositivo>) dispositivoRepository.findAll();
		Dispositivo dispositivo = null;
				//dispositivoRepository.findDispositivoUserLogado(user);
		//if (dispositivo != null && !dispositivos.isEmpty()) {
		if (dispositivo != null) {
			logUser("enviando ao user: ", user);
			try {
				FirebaseSender firebaseSender = new FirebaseSender(firebaseService.getConfig(),
						new DispositivoService(this.userRepository, this.dispositivoRepository, this.firebaseService));
				//firebaseSender.envia(dispositivos, new AlunoSync(alunos));
				//firebaseSender.envia(dispositivo, new UserSync(user));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Dispositivo> getLista(){
		return dispositivoRepository.getLista();
	}
		
	public boolean existe(String id) {
		return dispositivoRepository.existeId(id);
	}
	
	public Dispositivo busca(String id) {
		return dispositivoRepository.findID(id);
	}

	public void remove(String token) {
		System.out.println(token);
		dispositivoRepository.remove(token);
	}
	

	private void logUser(String mensagem, User user) {
		LOGGER.info(mensagem);
		LOGGER.info(user.getId());
	}

	public void deleta(List<Dispositivo> invalidos) {
		for(Dispositivo dispositivo : invalidos) {
			dispositivoRepository.delete(dispositivo);
		}	
	}
	
	public void deleta(Dispositivo invalido) {
		dispositivoRepository.delete(invalido);	
	}

}
