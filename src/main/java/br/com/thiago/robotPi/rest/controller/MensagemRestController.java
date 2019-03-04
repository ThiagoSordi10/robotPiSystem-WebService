package br.com.thiago.robotPi.rest.controller;

import static br.com.thiago.robotPi.utils.HTTPValues.JSON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.robotPi.dto.MensagemSync;
import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.service.ComandoService;
import br.com.thiago.robotPi.service.MensagemService;
import br.com.thiago.robotPi.service.RaspberryService;
import br.com.thiago.robotPi.service.UserService;

@RestController
@RequestMapping("api/mensagem")
public class MensagemRestController {
	
	private ComandoService comandoService;
	private MensagemService mensagemService;
	private UserService userService;
	private RaspberryService raspberryService;

	@Autowired
	public MensagemRestController(ComandoService comandoService, MensagemService mensagemService,
			UserService userService, RaspberryService raspberryService) {
		this.comandoService = comandoService;
		this.mensagemService = mensagemService;
		this.userService = userService;
		this.raspberryService = raspberryService;
	}
	
	@RequestMapping(value = "chat", method = GET, produces = JSON)//usando
	public @ResponseBody MensagemSync getMensagens(@RequestHeader("idUser") String idUser,
			@RequestHeader("idRaspberry") String idRaspberry) {
		User user = userService.busca(idUser);
		Raspberry raspberry = raspberryService.busca(idRaspberry);
		return new MensagemSync(mensagemService.getMensagens(user, raspberry));
	}
	
	@RequestMapping(method = POST, consumes = JSON, produces = JSON)//usando
	public @ResponseBody MensagemSync insereComandoEMensagem(@RequestBody Mensagem mensagem) {
		List<Mensagem> mensagens = new ArrayList<Mensagem>();
		Comando comando = mensagem.getComando();
		comandoService.salvaComando(comando);
		
		mensagem.setComando(null);
		mensagem = mensagemService.salvaMensagem(mensagem);
		if(mensagem != null) {
			Mensagem mensagemServer = mensagemService.enviaResposta(mensagem);
			mensagens.add(mensagem);
			mensagens.add(mensagemServer);
			return new MensagemSync(mensagens);
		}
		return null;
	}

}
