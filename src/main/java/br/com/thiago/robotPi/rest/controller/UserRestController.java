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

import br.com.thiago.robotPi.dto.UserSync;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.repository.UserRepository;
import br.com.thiago.robotPi.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = GET)
	public @ResponseBody UserSync lista() {
		return userService.getSyncLista();
	}

	@RequestMapping(method = POST, consumes = JSON, produces = JSON)
	public @ResponseBody UserSync insereOuAltera(@RequestBody User user,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean temAtualizacao = userService.haveUpdate(datahora);
		System.out.print("AQUIIIII");
		String id = userService.salvaUser(user);
		User salvo = userService.busca(id);
		System.out.print(salvo);
		return new UserSync(salvo);
	}


	@RequestMapping(value = "{id}", method = DELETE)
	public @ResponseBody ResponseEntity<UserSync> remove(@PathVariable("id") String id,
			@RequestHeader(value = "datahora", required = false) String datahora) {
		//boolean haveUpdate = userService.haveUpdate(datahora);
		if (userService.existe(id)) {
			userService.remove(id);
			return new ResponseEntity<UserSync>(new UserSync(userService.busca(id)),
					HttpStatus.OK);
		}
		return new ResponseEntity<UserSync>(new UserSync(new User()),
				HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "find", method = GET, produces = JSON)
    public @ResponseBody UserSync findUser(@RequestHeader("email") String email) {
		UserSync usersync = userService.findUser(email);
		System.out.print(usersync.getUser());
		return usersync;
	}
	
	@RequestMapping(value = "desativa/{id}", method = PUT)
    public @ResponseBody ResponseEntity<UserSync> desativa(@PathVariable("id") String id) {
		if (userService.existe(id)) {
			userService.desativa(id);
			return new ResponseEntity<UserSync>(new UserSync(userService.busca(id)),
					HttpStatus.OK);
		}
		return new ResponseEntity<UserSync>(new UserSync(new User()),
				HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "ativa/{id}", method = PUT)
    public @ResponseBody ResponseEntity<UserSync> ativa(@PathVariable("id") String id) {
		if (userService.existe(id)) {
			userService.ativa(id);
			return new ResponseEntity<UserSync>(new UserSync(userService.busca(id)),
					HttpStatus.OK);
		}
		return new ResponseEntity<UserSync>(new UserSync(new User()),
				HttpStatus.FORBIDDEN);
	}

}
