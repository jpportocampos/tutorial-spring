package com.spring.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class TutorialSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialSpringApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		WebClient client = WebClient.create();
		
		return client;
	}

}
