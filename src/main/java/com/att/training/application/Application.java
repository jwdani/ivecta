package com.att.training.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages={"com.att.training"})
@EnableJpaRepositories("com.att.training.repository")
@EntityScan("com.att.training.model") 
public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);	
	}	
}
