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
import br.com.thiago.robotPi.service.EmpresaService;
import br.com.thiago.robotPi.service.EstacaoService;

@Controller
@RequestMapping("empresa")
public class EmpresaController {

	private EmpresaService empresaService;
	private EstacaoService estacaoService;

	@Autowired
	public EmpresaController(EmpresaService empresaService, EstacaoService estacaoService) {
		this.empresaService= empresaService;
		this.estacaoService = estacaoService;
	}

	@RequestMapping("form")
	public ModelAndView form(Empresa empresa) {
		return preparaEmpresaParaForm(empresa);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView busca(@PathVariable("id") String id) {
		Empresa empresa = empresaService.busca(id);
		//List<Estacao> estacoes = estacaoService.getLista(empresa);
		if (empresa == null) {
			return new ModelAndView("redirect:empresa");
		}
		return preparaEmpresaOption(empresa);
	}
	
	private ModelAndView preparaEmpresaOption(Empresa empresa) {
		ModelAndView mav = new ModelAndView("empresaOption");
		mav.addObject("empresa", empresa);
		return mav;
	}

	private ModelAndView preparaEmpresaParaForm(Empresa empresa) {
		ModelAndView mav = new ModelAndView("empresa/formulario");
		mav.addObject("empresa", empresa);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salva(@ModelAttribute("empresa") Empresa empresa, RedirectAttributes attributes) {
		empresaService.salvaEmpresa(empresa);
		attributes.addFlashAttribute("info", "empresa salva");
		return "redirect:empresa";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		return new ModelAndView("empresa/lista", "empresas", empresaService.getLista());
	}

}
