package lt.vilniustech.ezukauskas.library_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LibraryManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}
}
