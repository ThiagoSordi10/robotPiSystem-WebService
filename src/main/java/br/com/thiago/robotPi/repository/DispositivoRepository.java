package br.com.thiago.robotPi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.User;

public interface DispositivoRepository extends PagingAndSortingRepository<Dispositivo, String>{
	
	@Query("FROM Dispositivo d")
	List<Dispositivo> getLista();
	
	@Query("SELECT COUNT(d) > 0 FROM Dispositivo d WHERE d.id = :id")
	boolean existeId(@Param("id") String id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Dispositivo d WHERE d.token = :token")
	void remove(@Param("token") String token);
	
	@Query("FROM Dispositivo d WHERE d.id = :id")
	Dispositivo findID(@Param("id") String id);
	
}
