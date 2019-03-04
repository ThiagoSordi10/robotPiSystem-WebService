package br.com.thiago.robotPi.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.converter.DateConverter;
import br.com.thiago.robotPi.dto.ComandoSync;
import br.com.thiago.robotPi.dto.MensagemSync;
import br.com.thiago.robotPi.dto.RaspberrySync;
import br.com.thiago.robotPi.firebase.FirebaseSender;
import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.repository.ComandoRepository;
import br.com.thiago.robotPi.repository.MensagemRepository;
import br.com.thiago.robotPi.utils.UUIDUtils;

@Service
public class MensagemService {

	private MensagemRepository mensagemRepository;
	private static final Logger LOGGER = Logger.getLogger(MensagemService.class);
	private DispositivoService dispositivoService;
	private FirebaseService firebaseService;

	@Autowired
	public MensagemService(MensagemRepository mensagemRepository, DispositivoService dispositivoService,
			FirebaseService firebaseService) {
		this.mensagemRepository = mensagemRepository;
		this.dispositivoService = dispositivoService;
		this.firebaseService = firebaseService;
	}

	public Mensagem salvaMensagem(Mensagem mensagem) {
		mensagemRepository.save(mensagem);
		//mensagem = enviaResposta(mensagem);//só para testes
		LOGGER.info("Mensagem salva");
		return mensagem;
	}
	
	public List<Mensagem> getMensagens(User user, Raspberry raspberry) {
		List<Mensagem> mensagens =  mensagemRepository.getMensagens(user, raspberry);
		for(Mensagem mensagem : mensagens) {
			if(mensagem.getComando() != null)
			System.out.println(mensagem.getComando().getHorarioSaida());
		}
		return mensagens;
	}
	
	public Mensagem enviaResposta(Mensagem mensagemRecebida) {
		Mensagem mensagemEnviar = new Mensagem();
		mensagemEnviar.setEnvio(1);
		mensagemEnviar.setMensagem("Processando solicitação");
		mensagemEnviar.setUser(mensagemRecebida.getUser());
		mensagemEnviar.setRaspberry(mensagemRecebida.getRaspberry());
		
		return mensagemEnviar;
	}

}
