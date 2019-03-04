package br.com.thiago.robotPi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.service.DispositivoService;
import br.com.thiago.robotPi.service.UserService;

@Controller
@RequestMapping("dispositivo")
public class DispositivoController {

	private DispositivoService dispositivoService;

	@Autowired
	public DispositivoController(DispositivoService dispositivoService) {
		this.dispositivoService = dispositivoService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		return new ModelAndView("dispositivo/lista", "dispositivos", dispositivoService.getLista());
	}

}
