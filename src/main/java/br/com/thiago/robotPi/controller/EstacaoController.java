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

@Controller
@RequestMapping("estacao")
public class EstacaoController {

	private EstacaoService estacaoService;
	private EmpresaService empresaService;

	@Autowired
	public EstacaoController(EstacaoService estacaoService, EmpresaService empresaService) {
		this.estacaoService = estacaoService;
		this.empresaService = empresaService;
	}

	/*@RequestMapping("form")
	public ModelAndView form(Estacao estacao) {
		return preparaEstacaoParaForm(estacao);
	}*/
	
	@RequestMapping(value = "form/{id}", method = RequestMethod.GET)
	public ModelAndView form(@PathVariable("id") String id, Estacao estacao) {
		estacao = new Estacao();
		Empresa empresa = empresaService.busca(id);
		if (empresa == null) {
			return new ModelAndView("redirect:estacao/lista/"+id);
		}
		estacao.setEmpresa(empresa);
		return preparaEstacaoParaForm(estacao);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView busca(@PathVariable("id") String id) {
		Estacao estacao = estacaoService.busca(id);
		if (estacao == null) {
			return new ModelAndView("redirect:empresaOption");
		}
		return preparaEstacaoParaForm(estacao);
	}

	private ModelAndView preparaEstacaoParaForm(Estacao estacao) {
		ModelAndView mav = new ModelAndView("estacao/formulario");
		mav.addObject("estacao", estacao);
		return mav;
	}
	
	private ModelAndView preparaEstacoesComEmpresa(List<Estacao> estacoes, Empresa empresa) {
		ModelAndView mav = new ModelAndView("estacao/lista");
		mav.addObject("estacoes", estacoes);
		mav.addObject("empresa", empresa);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salva(@ModelAttribute("estacao") Estacao estacao, RedirectAttributes attributes) {
		System.out.println(estacao.getId());
		System.out.println(estacao.getEmpresa().getId());
		estacaoService.salvaEstacao(estacao);
		attributes.addFlashAttribute("info", "estação salva");
		return "redirect:estacao/lista/"+estacao.getEmpresa().getId();
	}

	@RequestMapping(value = "lista/{id}", method = RequestMethod.GET)
	public ModelAndView lista(@PathVariable("id") String id) {
		Empresa empresa = empresaService.busca(id);
		List<Estacao> estacoes = estacaoService.getLista(empresa);
		return preparaEstacoesComEmpresa(estacoes, empresa);
	}

}
