package br.com.thiago.robotPi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.thiago.robotPi.model.Empresa;
import br.com.thiago.robotPi.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

	@Query("FROM User u WHERE u.desativado = 0")
	List<User> visiveis();
	
	@Query("FROM User u WHERE u.email = :email AND u.desativado=0")
	User findUser(@Param("email") String email);
	
	@Query("FROM User u WHERE u.empresa = :empresa")
	List<User> usersFromEmpresa(@Param("empresa") Empresa empresa);
}
