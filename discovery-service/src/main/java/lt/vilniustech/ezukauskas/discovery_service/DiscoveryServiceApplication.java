package lt.vilniustech.ezukauskas.discovery_service;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}
}
