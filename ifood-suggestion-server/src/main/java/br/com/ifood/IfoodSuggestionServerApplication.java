package br.com.ifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IfoodSuggestionServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IfoodSuggestionServerApplication.class, args);
	}
}
