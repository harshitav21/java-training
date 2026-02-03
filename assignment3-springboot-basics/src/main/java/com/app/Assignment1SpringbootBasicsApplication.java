package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Assignment1SpringbootBasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Assignment1SpringbootBasicsApplication.class, args);
	}

}
