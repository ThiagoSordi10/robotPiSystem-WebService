package br.com.thiago.robotPi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Empresa;

public interface EstacaoRepository extends PagingAndSortingRepository<Estacao, String> {
	
	@Query("FROM Estacao e WHERE e.empresa = :empresa AND e NOT IN (SELECT r.estacao FROM Raspberry r WHERE r.empresa = :empresa)")
	List<Estacao> getLista(@Param("empresa") Empresa empresa);

	@Query("DELETE FROM Estacao e WHERE e.id = :id")
	void remove(@Param("id") String id);	

}
