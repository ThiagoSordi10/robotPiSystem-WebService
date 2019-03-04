package br.com.thiago.robotPi.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.thiago.robotPi.converter.DateConverter;
import br.com.thiago.robotPi.dto.ComandoSync;
import br.com.thiago.robotPi.dto.MensagemSync;
import br.com.thiago.robotPi.firebase.FirebaseSender;
import br.com.thiago.robotPi.firebase.PrepareToSend;
import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.repository.ComandoRepository;
import br.com.thiago.robotPi.tcp.ServerRaspberry;
import br.com.thiago.robotPi.utils.UUIDUtils;

@Service
public class ComandoService {

	private ComandoRepository comandoRepository;
	private static final Logger LOGGER = Logger.getLogger(ComandoService.class);
	private UUIDUtils uuidUtils;
	private PrepareToSend prepareToSend;
	private RaspberryService raspberryService;
	private MensagemService mensagemService;
	private ServerRaspberry server;

	@Autowired
	public ComandoService(ComandoRepository comandoRepository, UUIDUtils uuidUtils, 
			PrepareToSend prepareToSend, RaspberryService raspberryService, 
			MensagemService mensagemService) {
		this.comandoRepository = comandoRepository;
		this.uuidUtils = uuidUtils;
		this.prepareToSend = prepareToSend;
		this.raspberryService = raspberryService;
		this.mensagemService = mensagemService;
	}
		
	@Async
	public void executaComandos() {
		System.out.println("Executando thread busca comandos");
		server.startServer();
		while(true) {	
			Comando comando = comandoRepository.findComandosParaExecutar();
			if(comando != null) {
				//Enviar mensagem para usuario confirmar envio de raspberry primeira estação
				comando.setExecutado(1);
				comandoRepository.save(comando);
				enviarAlerta(comando, "Aviso: Confirma comando "
						+ "(Ida até "+comando.getEstacaoSaida().getNome()+"). Clique aqui");
			}
		}
	}
	
	@Async
	public String salvaComando(Comando comando) {
		Date data = new Date();
		if(!raspberryService.isConnected(comando.getRaspberry())) {
			enviarAlerta(comando, "Comando não aprovado:\nDrone não está mais conectado");
			return comando.getId();
			
		}else if(comando.getExecutado() == 2) {
			execucaoNow(comando);
			return comando.getId();
			
		}else if(data.before(comando.getHorarioSaida())) {
			execucaoFuture(comando);
			return comando.getId();
		}else {
			enviarAlerta(comando, "Comando não aprovado:\nData inválida");
			return comando.getId();
		}
		
	}
	
	//Executada no async
	public void execucaoFuture(Comando comando) {
		if(verificaDisponibilidadeComando(comando)) {
			comando = geraId(comando);
			comandoRepository.save(comando);
			LOGGER.info("Comando salvo " + comando.getId());
			enviarAlerta(comando, "Comando aprovado. Você deverá confirmar o comando");
			return;
		}
		enviarAlerta(comando, "Comando não aprovado:\nJá existe um comando agendado para este drone neste período");
	}

	//Executada no async
	public void execucaoNow(Comando comando) {
		if(verificaStatusRaspberry(comando)) {
			if(verificaDisponibilidadeEstacoesParaComandos(comando)) {	
				comando = geraId(comando);
				comandoRepository.save(comando);
				LOGGER.info("Comando salvo " + comando.getId());
				enviarAlerta(comando, "Comando aprovado.");
				enviaParaRaspberry(comando);
				return;
			}
			if(comando.getId().isEmpty()) {
				enviarAlerta(comando, "Comando não aprovado:\nEstação(ões) ocupada(s) agora ou nos próximos minutos");
				return;
			}else {
				//enviar comando a ser confirmado de novo mas esperar um tempo
				enviarAlerta(comando, "Estações ocupadas. Clique aqui para tentar novamente (Recomenda-se que espere alguns minutos)");
			}
		}
		if(comando.getId() == null) {
			enviarAlerta(comando, "Comando não aprovado:\nDrone executando outra tarefa");
		}else {
			//enviar comando a ser confirmado de novo mas esperar um tempo
			enviarAlerta(comando, "Drone ocupado. Clique aqui para tentar novamente (Recomenda-se que espere alguns minutos)");
		}
	}
	
	private void enviaParaRaspberry(Comando comando) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonComando = "";
		try {
			jsonComando = objectMapper.writeValueAsString(comando);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		jsonComando = "{'comandoSync': "+jsonComando+"}";
		System.out.println(jsonComando);
		
		String endereco = comando.getRaspberry().getEndereco();
		server.conexaoCliente(endereco, jsonComando);
	}
	
	//Executada no async - Verifica se as estações do comando serão usadas por outro dentro de 3 minutos ou se já estão ocupadas por um raspberry
	private boolean verificaDisponibilidadeEstacoesParaComandos(Comando comando) {
		DateConverter convert = new DateConverter();
		boolean primeiroVerificador = comandoRepository.verificaStatusEstacoes(comando.getEstacaoSaida(), 
				comando.getEstacaoChegada(), convert.convertToDatabaseColumn(comando.getHorarioSaida()));
		System.out.println(primeiroVerificador);
		boolean segundoVerificador = raspberryService.verificaoEstacoes(comando);
		System.out.println(primeiroVerificador);
		
		if(primeiroVerificador == false || segundoVerificador == false) {
			return false; //Não Pode usar
		}
		
		return true; //pode usar
	}
	
	//Executada no async - verifica se determinado raspberry está executando ou entrará em execução em 3 minutos
	private boolean verificaStatusRaspberry(Comando comando) {
		DateConverter convert = new DateConverter();
		boolean status = comandoRepository.verificaStatusRaspberry(comando.getRaspberry(), 
				convert.convertToDatabaseColumn(comando.getHorarioSaida()));
		return status;
	}

	//Executada no async - verifica se o raspberry terá execuções de outro comando com diferença de 5 minutos
	private boolean verificaDisponibilidadeComando(Comando comando) {
		DateConverter convert = new DateConverter();
		boolean status = comandoRepository.comandoDisponivel(comando.getRaspberry(), 
				convert.convertToDatabaseColumn(comando.getHorarioSaida()));
		return status;
	}

	@Async
	public void enviarAlerta(Comando comando, String msg) {
		FirebaseSender firebaseSender = prepareToSend.getFirebaseSender();
		
		Mensagem mensagem = new Mensagem();
		mensagem = preparaMensagem(comando, msg);
		mensagemService.salvaMensagem(mensagem);
		
		//if(raspberryService.hasConnection(comando.getRaspberry().getDispositivo())) {
			
			try {
				firebaseSender.enviaMensagem(comando.getRaspberry().getDispositivo(), mensagem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		
	}
	
	public Mensagem preparaMensagem(Comando comando, String msg) {
		Mensagem mensagem = new Mensagem();
		mensagem.setEnvio(1);
		mensagem.setMensagem(msg);
		if(comando.getId() != null) {
			mensagem.setComando(comando);
		}
		mensagem.setRaspberry(comando.getRaspberry());
		mensagem.setUser(comando.getUser());
		return mensagem;
	}


	public List<Comando> getLista(User user) {
		return comandoRepository.getLista(user);
	}

	@Transactional
	public void remove(String id) {
		comandoRepository.remove(id);
		LOGGER.info("Comando removido " + id);
	}

	public boolean existe(String id) {
		return comandoRepository.exists(id);
	}

	public Comando busca(String id) {
		return comandoRepository.findOne(id);
	}
	
	private Comando geraId(Comando comando) {
		String uuid = comando.getId();
		LOGGER.info("validando id: " + uuid);
		if (comando.getId() == null || !uuidUtils.validaUUID(uuid)) {
			LOGGER.info("gerando novo id");
			comando.setId(uuidUtils.UUIDGenerator());
			LOGGER.info("id gerado " + comando.getId());
		}
		return comando;
	}
	
	@Async
	public void findComandoExecutando(Raspberry raspberry) {
		Comando comando =  comandoRepository.findComandoExecutando(raspberry);
		if(comando != null) {
			if(raspberry.getEstacao() == comando.getEstacaoSaida()) {
				enviarAlerta(comando, "Aviso: Confirma comando "
						+ "(Ida até "+comando.getEstacaoChegada().getNome()+"). Clique aqui");
			} else if(raspberry.getEstacao() == comando.getEstacaoChegada()) {
				Date date = new Date();
				comando.setHorarioChegada(date);
				comandoRepository.save(comando);
				LOGGER.info("Comando salvo " + comando.getId());
				enviarAlerta(comando, "Comando finalizado às "+comando.getHorarioChegada()+".");
			}
		}
		
	}

	public void setServer(ServerRaspberry serverRaspberry) {
		this.server = serverRaspberry;
		
	}

}
