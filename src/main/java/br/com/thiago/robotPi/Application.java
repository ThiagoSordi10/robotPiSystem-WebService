package br.com.thiago.robotPi;

import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import br.com.thiago.robotPi.service.ComandoService;

@SpringBootApplication
@EnableAutoConfiguration	
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableAsync
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}	

	
	  @Bean
	  public Executor asyncExecutor() {
		  ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(10);
	        executor.setMaxPoolSize(10);
	        executor.setQueueCapacity(500);
	        executor.setThreadNamePrefix("Comando-");
	        executor.initialize();
	        return executor;
	  }
	
}
