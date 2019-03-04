package br.com.thiago.robotPi.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.dto.RaspberrySync;
import br.com.thiago.robotPi.dto.UserSync;
import br.com.thiago.robotPi.firebase.FirebaseSender;
import br.com.thiago.robotPi.firebase.PrepareToSend;
import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.service.EmpresaService;
import br.com.thiago.robotPi.repository.RaspberryRepository;
import br.com.thiago.robotPi.utils.UUIDUtils;

@Service
public class RaspberryService {

	private RaspberryRepository raspberryRepository;
	private EmpresaService empresaService;
	private EstacaoService estacaoService;
	private PrepareToSend prepareToSend;
	private static final Logger LOGGER = Logger.getLogger(RaspberryService.class);
	private UUIDUtils uuidUtils;

	@Autowired
	public RaspberryService(RaspberryRepository raspberryRepository, UUIDUtils uuidUtils, 
			EmpresaService empresaService, FirebaseService firebaseService,
			PrepareToSend prepareToSend, EstacaoService estacaoService) {
		this.raspberryRepository = raspberryRepository;
		this.uuidUtils = uuidUtils;
		this.empresaService = empresaService;
		this.prepareToSend = prepareToSend;
		this.estacaoService = estacaoService;
	}

	public String salvaRaspberry(Raspberry raspberry) {
		raspberry = geraId(raspberry);
		raspberryRepository.save(raspberry);
		LOGGER.info("Raspberry salvo " + raspberry.getId());
		enviaRaspberry(raspberry, "raspberrySync");
		return raspberry.getId();
	}
	
	@Async
	private void enviaRaspberry(Raspberry raspberry, String chave) {
		Empresa empresa = raspberry.getEmpresa();
		List<Dispositivo> dispositivos = empresaService.findDispositivos(empresa);
		List<Raspberry> raspberries = raspberryRepository.getLista(raspberry.getEmpresa());
		System.out.print("a");
		if (dispositivos != null && !dispositivos.isEmpty()) {
			try {
				FirebaseSender firebaseSender = prepareToSend.getFirebaseSender();
				firebaseSender.enviaRaspberry(dispositivos, new RaspberrySync(raspberries), chave);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	public List<Raspberry> getLista(Empresa empresa) {
		return raspberryRepository.getLista(empresa);
	}

	public long getTotal() {
		return raspberryRepository.count();
	}

	@Transactional
	public void remove(String id) {
	    raspberryRepository.delete(id);
		LOGGER.info("raspberry removido " + id);
	}

	public boolean existe(String id) {
		return raspberryRepository.exists(id);
	}

	public Raspberry busca(String id) {
		return raspberryRepository.findOne(id);
	}

	public RaspberrySync getSyncLista(String idEmpresa) {
		System.out.println(idEmpresa);
		Empresa empresa = empresaService.busca(idEmpresa);
		System.out.println(empresa);
		return new RaspberrySync(raspberryRepository.getLista(empresa));
	}

	private Raspberry geraId(Raspberry raspberry) {
		String uuid = raspberry.getId();
		LOGGER.info("validando id: " + uuid);
		if (raspberry.getId() == null || !uuidUtils.validaUUID(uuid)) {
			LOGGER.info("gerando novo id");
			raspberry.setId(uuidUtils.UUIDGenerator());
			LOGGER.info("id gerado " + raspberry.getId());
		}
		return raspberry;
	}
	
	public boolean hasConnection(Dispositivo dispositivo) {
		return raspberryRepository.findDispositivo(dispositivo) != null;
	}

	public Raspberry conexaoRaspberry(Raspberry raspberry) {
		raspberry = raspberryRepository.save(raspberry);
		return atualizaEnvia(raspberry);
	}

	public Raspberry desconecta(Dispositivo dispositivo) {
		Raspberry raspberry = raspberryRepository.findDispositivo(dispositivo);
		raspberry.setDispositivo(null);
		return atualizaEnvia(raspberry);
	}
	
	public Raspberry atualizaEnvia(Raspberry raspberry) {
		raspberry = raspberryRepository.save(raspberry);
		enviaRaspberry(raspberry, "raspberrySync");
		return raspberry;
	}

	public boolean verificaoEstacoes(Comando comando) {
		List<Raspberry> raspberries = getLista(comando.getRaspberry().getEmpresa());
		for(Raspberry raspberry : raspberries) {
			if(raspberry.getEstacao() != null) {
				if(comando.getEstacaoSaida().equals(raspberry.getEstacao()) || 
						comando.getEstacaoChegada().equals(raspberry.getEstacao())){
					return false;
				}
			}
		}
		return true;
	}

	public boolean isConnected(Raspberry raspberry) {
		if(raspberryRepository.verificaConexao(raspberry.getId())) {
			return true;
		}
		return false;
	}

	public Raspberry findByAddress(InetAddress inetAddress) {
		return raspberryRepository.findAddress(inetAddress.getHostAddress());
		
	}
	

}
