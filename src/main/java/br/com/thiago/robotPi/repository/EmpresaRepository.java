package br.com.thiago.robotPi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;

public interface EmpresaRepository  extends PagingAndSortingRepository<Empresa, String> {
	
	@Query("FROM Empresa e")
	List<Empresa> getLista();
	
	@Query("DELETE FROM Empresa e WHERE e.id = :id")
	void remove(@Param("id") String id);
	
	@Query("FROM Empresa e where e.nome = :nome and e.cep = :cep")
	Empresa findID(@Param("nome") String nome, @Param("cep") String cep);
	
	@Query("FROM Empresa e where e.id = :id")
	Empresa findEmpresa(@Param("id") String idEmpresa);

	@Query("SELECT d FROM Empresa e, User u, Dispositivo d WHERE d.user = u AND u.empresa = :empresa")
	List<Dispositivo> findDispositivos(@Param("empresa") Empresa empresa);

}
