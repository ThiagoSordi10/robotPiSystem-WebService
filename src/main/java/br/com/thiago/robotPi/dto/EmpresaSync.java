package br.com.thiago.robotPi.dto;


import java.util.List;


import br.com.thiago.robotPi.model.Empresa;

public class EmpresaSync {

	private final List<Empresa> empresas;
	private Empresa empresa;

	public EmpresaSync(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public EmpresaSync(Empresa empresa) {
		this.empresas = null;
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
