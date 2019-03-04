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
import br.com.thiago.robotPi.dto.DispositivoSync;
import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.service.ComandoService;
import br.com.thiago.robotPi.service.DispositivoService;

@RestController
@RequestMapping("api/dispositivo")
public class DispositivoRestController {

	private DispositivoService dispositivoService;

	@Autowired
	public DispositivoRestController(DispositivoService dispositivoService) {
		this.dispositivoService = dispositivoService;
	}


	@RequestMapping(value = "{token}", method = DELETE)
	public @ResponseBody ResponseEntity<DispositivoSync> remove(@PathVariable("token") String token) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		if (dispositivoService.existe(token)) {
			dispositivoService.remove(token);
			return new ResponseEntity<DispositivoSync>(new DispositivoSync(dispositivoService.busca(token)),
					HttpStatus.OK);
		}
		return new ResponseEntity<DispositivoSync>(new DispositivoSync(new Dispositivo()),
				HttpStatus.FORBIDDEN);
	}


}
