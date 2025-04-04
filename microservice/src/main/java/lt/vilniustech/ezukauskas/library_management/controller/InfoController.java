package lt.vilniustech.ezukauskas.library_management.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/info")
    public String info() {
        return "Microservice instance running on port: " + port;
    }
}
