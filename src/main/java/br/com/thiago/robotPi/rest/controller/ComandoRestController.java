package br.com.thiago.robotPi.rest.controller;

import static br.com.thiago.robotPi.utils.HTTPValues.JSON;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.robotPi.dto.ComandoSync;
import br.com.thiago.robotPi.dto.MensagemSync;
import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.service.ComandoService;
import br.com.thiago.robotPi.service.MensagemService;
import br.com.thiago.robotPi.service.RaspberryService;
import br.com.thiago.robotPi.service.UserService;

@RestController
@RequestMapping("api/comando")
public class ComandoRestController {

	private ComandoService comandoService;
	private MensagemService mensagemService;
	private UserService userService;
	private RaspberryService raspberryService;

	@Autowired
	public ComandoRestController(ComandoService comandoService, MensagemService mensagemService,
			UserService userService, RaspberryService raspberryService) {
		this.comandoService = comandoService;
		this.mensagemService = mensagemService;
		this.userService = userService;
		this.raspberryService = raspberryService;
	}

	@RequestMapping(method = GET)
	public @ResponseBody ComandoSync lista(@RequestBody User user) {
		return new ComandoSync(comandoService.getLista(user));
	}

	@RequestMapping(value = "{id}", method = DELETE)
	public @ResponseBody ResponseEntity<ComandoSync> remove(@PathVariable("id") String id,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		if (comandoService.existe(id)) {
			comandoService.remove(id);
			return new ResponseEntity<ComandoSync>(new ComandoSync(comandoService.busca(id)),
					HttpStatus.OK);
		}
		return new ResponseEntity<ComandoSync>(new ComandoSync(new Comando()),
				HttpStatus.FORBIDDEN);
	}
		
}
