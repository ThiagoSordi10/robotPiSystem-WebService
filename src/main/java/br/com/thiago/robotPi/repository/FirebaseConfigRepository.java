package br.com.thiago.robotPi.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.thiago.robotPi.firebase.FirebaseConfig;

public interface FirebaseConfigRepository extends CrudRepository<FirebaseConfig, Long> {

}
