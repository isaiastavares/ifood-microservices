package br.com.ifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class IfoodEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfoodEurekaServerApplication.class, args);
	}
}
