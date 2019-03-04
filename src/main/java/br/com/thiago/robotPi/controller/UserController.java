package br.com.thiago.robotPi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.User;
import br.com.thiago.robotPi.service.EmpresaService;
import br.com.thiago.robotPi.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	private UserService userService;
	private EmpresaService empresaService;

	@Autowired
	public UserController(UserService userService, EmpresaService empresaService) {
		this.userService = userService;
		this.empresaService = empresaService;
	}

	@RequestMapping("form")
	public ModelAndView form(User user) {
		return preparaUsuarioParaForm(user);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView busca(@PathVariable("id") String id) {
		User user = userService.busca(id);
		if (user == null) {
			return new ModelAndView("redirect:user");
		}
		return preparaUsuarioParaForm(user);
	}

	private ModelAndView preparaUsuarioParaForm(User user) {
		ModelAndView mav = new ModelAndView("user/formulario");
		mav.addObject("user", user);
		return mav;
	}
	
	private ModelAndView preparaUsersComEmpresa(List<User> users, Empresa empresa) {
		ModelAndView mav = new ModelAndView("user/lista");
		mav.addObject("users", users);
		mav.addObject("empresa", empresa);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salva(@ModelAttribute("user") User user, RedirectAttributes attributes) {
		userService.salvaUser(user);
		attributes.addFlashAttribute("info", "user salvo");
		return "redirect:user/lista/"+user.getEmpresa().getId();
	}

	@RequestMapping(value = "lista/{id}", method = RequestMethod.GET)
	public ModelAndView lista(@PathVariable("id") String id) {
		Empresa empresa = empresaService.busca(id);
		List<User> users = userService.getLista(empresa);
		return preparaUsersComEmpresa(users, empresa);
	}

}
