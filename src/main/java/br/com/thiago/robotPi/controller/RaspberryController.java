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
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.service.EmpresaService;
import br.com.thiago.robotPi.service.EstacaoService;
import br.com.thiago.robotPi.service.RaspberryService;

@Controller
@RequestMapping("raspberry")
public class RaspberryController {

	private RaspberryService raspberryService;
	private EmpresaService empresaService;

	@Autowired
	public RaspberryController(RaspberryService raspberryService, EmpresaService empresaService) {
		this.raspberryService = raspberryService;
		this.empresaService = empresaService;
	}

	/*@RequestMapping("form")
	public ModelAndView form(Estacao estacao) {
		return preparaEstacaoParaForm(estacao);
	}*/
	
	@RequestMapping(value = "form/{id}", method = RequestMethod.GET)
	public ModelAndView form(@PathVariable("id") String id, Raspberry raspberry) {
		raspberry = new Raspberry();
		Empresa empresa = empresaService.busca(id);
		if (empresa == null) {
			return new ModelAndView("redirect:raspberry/lista/"+id);
		}
		raspberry.setEmpresa(empresa);
		return preparaRaspberryParaForm(raspberry);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView busca(@PathVariable("id") String id) {
		Raspberry raspberry = raspberryService.busca(id);
		if (raspberry == null) {
			return new ModelAndView("redirect:empresaOption");
		}
		return preparaRaspberryParaForm(raspberry);
	}

	private ModelAndView preparaRaspberryParaForm(Raspberry raspberry) {
		ModelAndView mav = new ModelAndView("raspberry/formulario");
		mav.addObject("raspberry", raspberry);
		return mav;
	}
	
	
	private ModelAndView preparaRaspberriesComEmpresa(List<Raspberry> raspberries, Empresa empresa) {
		ModelAndView mav = new ModelAndView("raspberry/lista");
		mav.addObject("raspberries", raspberries);
		mav.addObject("empresa", empresa);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salva(@ModelAttribute("raspberry") Raspberry raspberry, RedirectAttributes attributes) {
		raspberryService.salvaRaspberry(raspberry);
		attributes.addFlashAttribute("info", "raspberry salvo");
		return "redirect:raspberry/lista/"+raspberry.getEmpresa().getId();
	}

	@RequestMapping(value = "lista/{id}", method = RequestMethod.GET)
	public ModelAndView lista(@PathVariable("id") String id) {
		Empresa empresa = empresaService.busca(id);
		List<Raspberry> raspberries = raspberryService.getLista(empresa);
		return preparaRaspberriesComEmpresa(raspberries, empresa);
	}
}
