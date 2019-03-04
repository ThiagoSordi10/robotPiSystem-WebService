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

import br.com.thiago.robotPi.dto.RaspberrySync;
import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.service.RaspberryService;

@RestController
@RequestMapping("api/raspberry")
public class RaspberryRestController {

	private RaspberryService raspberryService;

	@Autowired
	public RaspberryRestController(RaspberryService raspberryService) {
		this.raspberryService = raspberryService;
	}

	@RequestMapping(method = GET, produces = JSON)
	public @ResponseBody RaspberrySync lista(@RequestHeader("idEmpresa") String idEmpresa) {
		return raspberryService.getSyncLista(idEmpresa);
	}
	
	/*@RequestMapping(method = POST, consumes = JSON, produces = JSON)
	public @ResponseBody RaspberrySync insereOuAltera(@RequestBody Raspberry raspberry,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		String id = raspberryService.salvaRaspberry(raspberry);
		Raspberry salvo = raspberryService.busca(id);
		System.out.print(salvo);
		return new RaspberrySync(salvo);
	}*/

	@RequestMapping(value = "{id}", method = DELETE)
	public @ResponseBody ResponseEntity<RaspberrySync> remove(@PathVariable("id") String id,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		if (raspberryService.existe(id)) {
			raspberryService.remove(id);
			return new ResponseEntity<RaspberrySync>(new RaspberrySync(raspberryService.busca(id)),
					HttpStatus.OK);
		}
		return new ResponseEntity<RaspberrySync>(new RaspberrySync(new Raspberry()),
				HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "conexao", method = POST, consumes = JSON, produces = JSON)
	public @ResponseBody RaspberrySync conectarRaspberry(@RequestBody Raspberry raspberry) {
		return new RaspberrySync(raspberryService.conexaoRaspberry(raspberry));
	}
	
	@RequestMapping(value = "desconexao", method = POST, consumes = JSON, produces = JSON)
	public @ResponseBody RaspberrySync desconectarRaspberry(@RequestBody Dispositivo dispositivo) {
		return new RaspberrySync(raspberryService.desconecta(dispositivo));
	}


}
