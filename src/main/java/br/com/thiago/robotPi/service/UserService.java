package br.com.thiago.robotPi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.robotPi.dto.UserSync;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.repository.UserRepository;
import br.com.thiago.robotPi.utils.UUIDUtils;

@Service
public class UserService {

	private UserRepository userRepository;
	private DispositivoService dispositivoService;
	private static final Logger LOGGER = Logger.getLogger(UserService.class);
	private UUIDUtils uuidUtils;

	@Autowired
	public UserService(UserRepository userRepository, 
			DispositivoService dispositivoService, 
			UUIDUtils uuidUtils) {
		this.userRepository = userRepository;
		this.dispositivoService = dispositivoService;
		this.uuidUtils = uuidUtils;
	}

	public String salvaUser(User user) {
		user = geraId(user);
		userRepository.save(user);
		LOGGER.info("Usuário salvo " + user.getId());

		//notificaAlteracao(user);
		return user.getId();
	}

	public List<User> getLista(Empresa empresa) {
		return userRepository.usersFromEmpresa(empresa);
	}

	public long getTotal() {
		return userRepository.count();
	}

	@Transactional
	public void remove(String id) {
		User user = busca(id);
		user.setDesativado(1);
		LOGGER.info("aluno removido " + id);
		salvaUser(user);
	}

	public boolean existe(String id) {
		return userRepository.exists(id);
	}

	public User busca(String id) {
		return userRepository.findOne(id);
	}

	public UserSync getSyncLista() {
		return new UserSync(userRepository.visiveis());
	}

	/*public UserSync novosRegistro(LocalDateTime datahora) {
		List<User> users = userRepository.modificados(datahora);
		return new UserSync(users);
	}*/
	
	public UserSync findUser(String email) {
		User user = userRepository.findUser(email);
		return new UserSync(user);
	}

	private void logUsers(String mensagem, List<User> users) {
		LOGGER.info(mensagem);
		users.forEach(user -> LOGGER.info(user.getId()));
	}

	private void notificaAlteracao(User user) {
		dispositivoService.enviaNotificacao(user);
	}

	private User geraId(User user) {
		String uuid = user.getId();
		LOGGER.info("validando id: " + uuid);
		if (user.getId() == null || !uuidUtils.validaUUID(uuid)) {
			LOGGER.info("gerando novo id");
			user.setId(uuidUtils.UUIDGenerator());
			LOGGER.info("id gerado " + user.getId());
		}
		return user;
	}

	public void desativa(String id) {
		User user = busca(id);
		user.setDesativado(1);
		userRepository.save(user);
	}
	
	public void ativa(String id) {
		User user = busca(id);
		user.setDesativado(0);
		userRepository.save(user);
	}

}
