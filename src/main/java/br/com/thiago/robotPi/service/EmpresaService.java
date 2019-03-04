package br.com.thiago.robotPi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.dto.EmpresaSync;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.repository.EmpresaRepository;
import br.com.thiago.robotPi.utils.UUIDUtils;

@Service
public class EmpresaService {

	private EmpresaRepository empresaRepository;
	private static final Logger LOGGER = Logger.getLogger(EmpresaService.class);
	private UUIDUtils uuidUtils;

	@Autowired
	public EmpresaService(EmpresaRepository empresaRepository, UUIDUtils uuidUtils) {
		this.empresaRepository = empresaRepository;
		this.uuidUtils = uuidUtils;
	}

	public String salvaEmpresa(Empresa empresa) {
		empresa = geraId(empresa);
		empresaRepository.save(empresa);
		LOGGER.info("Empresa salva " + empresa.getId());
		return empresa.getId();
	}

	public List<Empresa> getLista() {
		return empresaRepository.getLista();
	}

	public long getTotal() {
		return empresaRepository.count();
	}

	@Transactional
	public void remove(String id) {
		empresaRepository.remove(id);
		LOGGER.info("Empresa " + id);
	}

	public boolean existe(String id) {
		return empresaRepository.exists(id);
	}

	public Empresa busca(String id) {
		return empresaRepository.findOne(id);
	}

	public EmpresaSync getSyncLista() {
		return new EmpresaSync(empresaRepository.getLista());
	}
	
	public EmpresaSync findID(String nome, String cep) {
		Empresa empresa = empresaRepository.findID(nome, cep);
		return new EmpresaSync(empresa);
	}


	private void logEmpresas(String mensagem, List<Empresa> empresas) {
		LOGGER.info(mensagem);
		empresas.forEach(empresa -> LOGGER.info(empresa.getId()));
	}

	private Empresa geraId(Empresa empresa) {
		String uuid = empresa.getId();
		LOGGER.info("validando id: " + uuid);
		if (empresa.getId() == null || !uuidUtils.validaUUID(uuid)) {
			LOGGER.info("gerando novo id");
			empresa.setId(uuidUtils.UUIDGenerator());
			LOGGER.info("id gerado " + empresa.getId());
		}
		return empresa;
	}

	public List<Dispositivo> findDispositivos(Empresa empresa) {
		return empresaRepository.findDispositivos(empresa);
	}

}
