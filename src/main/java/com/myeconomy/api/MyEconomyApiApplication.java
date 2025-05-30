package com.myeconomy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.myeconomy")
@EntityScan("com.myeconomy.model")
@EnableJpaRepositories("com.myeconomy.repository")
public class MyEconomyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyEconomyApiApplication.class, args);
	}

}
