package br.com.thiago.robotPi;

import java.util.logging.Logger;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.thiago.robotPi.service.ComandoService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	@Autowired
	private ComandoService comandoService;
    public static int counter;
 
    @Override
    public void run(String...args) throws Exception {
       comandoService.executaComandos();
    }
}
