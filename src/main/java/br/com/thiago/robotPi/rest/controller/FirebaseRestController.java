package br.com.thiago.robotPi.rest.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.service.DispositivoService;

@Controller
@RequestMapping("api/firebase")
public class FirebaseRestController {

	private DispositivoService dispositivoService;

	@Autowired
	public FirebaseRestController(DispositivoService dispositivoService) {
		this.dispositivoService = dispositivoService;
	}

	@RequestMapping(value = "dispositivo", method = POST)
	public ResponseEntity<String> salvaDispositivo(@RequestBody Dispositivo dispositivo) {
		//token = token.substring(1);
		//token = token.substring(0, token.length()-1);
		dispositivoService.salva(dispositivo);
		return new ResponseEntity<String>("dispositivo cadastrado", HttpStatus.OK);
	}
	
	@RequestMapping(value = "{token}", method = DELETE)
	public ResponseEntity<String> removeUserToken(@PathVariable("token") String token) {
		dispositivoService.remove(token);
		return new ResponseEntity<String>("dispositivo removido", HttpStatus.OK);
	}
}
