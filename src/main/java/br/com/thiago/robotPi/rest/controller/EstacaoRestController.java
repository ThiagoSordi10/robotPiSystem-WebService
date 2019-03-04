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

import br.com.thiago.robotPi.dto.EstacaoSync;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.service.EstacaoService;

@RestController
@RequestMapping("api/estacao")
public class EstacaoRestController {

	private EstacaoService estacaoService;

	@Autowired
	public EstacaoRestController(EstacaoService estacaoService) {
		this.estacaoService = estacaoService;
	}

	@RequestMapping(method = GET)
	public @ResponseBody EstacaoSync lista(@RequestHeader("idEmpresa") String idEmpresa) {
		return estacaoService.getSyncLista(idEmpresa);
	}

	
	/*@RequestMapping(method = POST, consumes = JSON, produces = JSON)
	public @ResponseBody EstacaoSync insereOuAltera(@RequestBody Estacao estacao,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean temAtualizacao = userService.haveUpdate(datahora);
		System.out.print("AQUIIIII");
		String id = estacaoService.salvaEstacao(estacao);
		Estacao salvo = estacaoService.busca(id);
		System.out.print(salvo);
		return new EstacaoSync(salvo);
	}

	@RequestMapping(value = "{id}", method = GET, produces = JSON)
	public @ResponseBody EstacaoSync busca(@PathVariable("id") String id) {
		return new EstacaoSync(estacaoService.busca(id));
	}*/

	@RequestMapping(value = "{id}", method = DELETE)
	public @ResponseBody ResponseEntity<EstacaoSync> remove(@PathVariable("id") String id,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		if (estacaoService.existe(id)) {
			estacaoService.remove(id);
			return new ResponseEntity<EstacaoSync>(new EstacaoSync(new Estacao()),
					HttpStatus.OK);
		}
		return new ResponseEntity<EstacaoSync>(new EstacaoSync(new Estacao()),
				HttpStatus.FORBIDDEN);
	}

	/*@RequestMapping(value = "{id}", method = PUT, consumes = JSON, produces = JSON)
	public @ResponseBody EstacaoSync insereOuAltera(@PathVariable("id") String id, @RequestBody Estacao estacao,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		estacao.setId(id);
		String idSalvo = estacaoService.salvaEstacao(estacao);
		List<Estacao> estacoes = new ArrayList<>(Arrays.asList(estacaoService.busca(idSalvo)));
		return new EstacaoSync(estacoes);
	}*/


}
