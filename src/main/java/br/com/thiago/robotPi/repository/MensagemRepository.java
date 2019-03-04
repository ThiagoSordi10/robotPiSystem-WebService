package br.com.thiago.robotPi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Comando;
import br.com.thiago.robotPi.model.Estacao;
import br.com.thiago.robotPi.model.Mensagem;
import br.com.thiago.robotPi.model.Raspberry;
import br.com.thiago.robotPi.model.User;

public interface MensagemRepository extends PagingAndSortingRepository<Mensagem, String> {
	
	@Query("FROM Mensagem m WHERE m.user = :user AND m.raspberry = :raspberry")
	List<Mensagem> getMensagens(@Param("user") User user, @Param("raspberry") Raspberry raspberry);

}
