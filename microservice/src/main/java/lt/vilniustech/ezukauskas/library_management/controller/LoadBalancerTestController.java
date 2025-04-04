package lt.vilniustech.ezukauskas.library_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoadBalancerTestController {

    private final RestTemplate restTemplate;

    @Autowired
    public LoadBalancerTestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/lbtest")
    public String testLoadBalancer() {
        String url = "http://library-management/info";
        return restTemplate.getForObject(url, String.class);
    }
}
