package br.com.thiago.robotPi.repository;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Dispositivo;
import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.Raspberry;

public interface RaspberryRepository extends PagingAndSortingRepository<Raspberry, String> {
	
	@Query("FROM Raspberry r WHERE r.empresa = :empresa")
	List<Raspberry> getLista(@Param("empresa") Empresa empresa);
	
	@Query("DELETE FROM Raspberry r WHERE r.id = :id")
	void remove(@Param("id") String id);

	@Query("FROM Raspberry r WHERE r.dispositivo = :dispositivo")
	Raspberry findDispositivo(@Param("dispositivo") Dispositivo dispositivo);

	@Query("SELECT COUNT(r) > 0 FROM Raspberry r WHERE r.id = :idRaspberry AND r.endereco != null")
	boolean verificaConexao(@Param("idRaspberry") String idRaspberry);

	@Query("FROM Raspberry r WHERE r.endereco = :endereco")
	Raspberry findAddress(@Param("endereco") String hostAddress);

}
