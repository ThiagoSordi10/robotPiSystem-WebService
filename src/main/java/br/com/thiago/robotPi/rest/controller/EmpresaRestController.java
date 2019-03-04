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

import br.com.thiago.robotPi.dto.EmpresaSync;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.service.EmpresaService;

@RestController
@RequestMapping("api/empresa")
public class EmpresaRestController {

	private EmpresaService empresaService;

	@Autowired
	public EmpresaRestController(EmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	@RequestMapping(method = GET)
	public @ResponseBody EmpresaSync lista() {
		return empresaService.getSyncLista();
	}

	
	/*@RequestMapping(method = POST, consumes = JSON, produces = JSON)
	public @ResponseBody EmpresaSync insereOuAltera(@RequestBody Empresa empresa,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean temAtualizacao = userService.haveUpdate(datahora);
		System.out.print("AQUIIIII");
		String id = empresaService.salvaEmpresa(empresa);
		Empresa salvo = empresaService.busca(id);
		System.out.print(salvo);
		return new EmpresaSync(salvo);
	}

	@RequestMapping(value = "{id}", method = GET, produces = JSON)
	public @ResponseBody EmpresaSync busca(@PathVariable("id") String id) {
		return new EmpresaSync(empresaService.busca(id));
	}*/

	@RequestMapping(value = "{id}", method = DELETE)
	public @ResponseBody ResponseEntity<EmpresaSync> remove(@PathVariable("id") String id,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		if (empresaService.existe(id)) {
			empresaService.remove(id);
			return new ResponseEntity<EmpresaSync>(new EmpresaSync(empresaService.busca(id)),
					HttpStatus.OK);
		}
		return new ResponseEntity<EmpresaSync>(new EmpresaSync(new Empresa()),
				HttpStatus.FORBIDDEN);
	}

	/*@RequestMapping(value = "{id}", method = PUT, consumes = JSON, produces = JSON)
	public @ResponseBody EmpresaSync insereOuAltera(@PathVariable("id") String id, @RequestBody Empresa empresa,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		empresa.setId(id);
		String idSalvo = empresaService.salvaEmpresa(empresa);
		List<Empresa> empresas = new ArrayList<>(Arrays.asList(empresaService.busca(idSalvo)));
		return new EmpresaSync(empresas);
	}
	
	@RequestMapping(value = "user", method = GET, produces = JSON)
    public @ResponseBody EmpresaSync findID(@RequestHeader("nome") String nome, 
    		@RequestHeader("cep") String cep) {
		return empresaService.findID(nome, cep);
	}*/

}
