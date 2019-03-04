package br.com.thiago.robotPi.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;

public interface ComandoRepository extends PagingAndSortingRepository<Comando, String> {
	
	@Query("FROM Comando c WHERE c.user = :user")
	List<Comando> getLista(@Param("user") User user);

	@Query("DELETE FROM Comando c WHERE c.id = :id")
	void remove(@Param("id") String id);
	
	@Query("SELECT COUNT(c) = 0 FROM Comando c WHERE c.raspberry = :raspberry AND c.executado < 3 AND (time_to_sec(TIMEDIFF(c.horarioSaida, :horarioSaida))/60) BETWEEN -5 AND 5")
	boolean comandoDisponivel(@Param("raspberry") Raspberry raspberry, @Param("horarioSaida") Timestamp horarioSaida);
	
	@Query("SELECT COUNT(c) = 0 FROM Comando c WHERE c.raspberry = :raspberry AND (c.executado = 2 OR (time_to_sec(TIMEDIFF(c.horarioSaida, :horarioSaida))/60) BETWEEN 0 AND 3)")
	boolean verificaStatusRaspberry(@Param("raspberry") Raspberry raspberry, @Param("horarioSaida") Timestamp horarioSaida);
	
	@Query("SELECT COUNT(c) = 0 FROM Comando c WHERE (c.estacaoSaida = :estacaoSaida OR "
			+ "c.estacaoChegada = :estacaoSaida OR c.estacaoChegada = :estacaoChegada OR "
			+ "c.estacaoSaida = :estacaoChegada) AND (time_to_sec(TIMEDIFF(c.horarioSaida, :horarioSaida))/60) BETWEEN 0 AND 3")
	boolean verificaStatusEstacoes(@Param("estacaoSaida") Estacao estacaoSaida, @Param("estacaoChegada") Estacao estacaoChegada,
			 @Param("horarioSaida") Timestamp horarioSaida);

	@Query("SELECT DISTINCT c FROM Comando c WHERE c.executado = 0 AND c.horarioSaida = NOW()")
	Comando findComandosParaExecutar();
	
	@Query("FROM Comando c WHERE c.executado = 2 AND c.raspberry = :raspberry")
	Comando findComandoExecutando(@Param("raspberry") Raspberry raspberry);


}
