package br.com.thiago.robotPi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.dto.EstacaoSync;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.repository.EstacaoRepository;
import br.com.thiago.robotPi.utils.UUIDUtils;

@Service
public class EstacaoService {

	private EstacaoRepository estacaoRepository;
	private EmpresaService empresaService;
	private static final Logger LOGGER = Logger.getLogger(EstacaoService.class);
	private UUIDUtils uuidUtils;

	@Autowired
	public EstacaoService(EstacaoRepository estacaoRepository, UUIDUtils uuidUtils, EmpresaService empresaService) {
		this.estacaoRepository = estacaoRepository;
		this.uuidUtils = uuidUtils;
		this.empresaService = empresaService;
	}

	public String salvaEstacao(Estacao estacao) {
		estacao = geraId(estacao);
		estacaoRepository.save(estacao);
		LOGGER.info("Estação salva " + estacao.getId());
		return estacao.getId();
	}

	public List<Estacao> getLista(Empresa empresa) {
		return estacaoRepository.getLista(empresa);
	}

	@Transactional
	public void remove(String id) {
		estacaoRepository.delete(id);
		LOGGER.info("Estação removida " + id);
	}

	public boolean existe(String id) {
		return estacaoRepository.exists(id);
	}

	public Estacao busca(String id) {
		return estacaoRepository.findOne(id);
	}

	public EstacaoSync getSyncLista(String idEmpresa) {
		Empresa empresa = empresaService.busca(idEmpresa);
		return new EstacaoSync(estacaoRepository.getLista(empresa));
	}
	
	private Estacao geraId(Estacao estacao) {
		String uuid = estacao.getId();
		LOGGER.info("validando id: " + uuid);
		if (estacao.getId() == null || !uuidUtils.validaUUID(uuid)) {
			LOGGER.info("gerando novo id");
			estacao.setId(uuidUtils.UUIDGenerator());
			LOGGER.info("id gerado " + estacao.getId());
		}
		return estacao;
	}

}
