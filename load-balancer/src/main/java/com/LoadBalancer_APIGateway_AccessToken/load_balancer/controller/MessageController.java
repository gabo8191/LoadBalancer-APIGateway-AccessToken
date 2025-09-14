package com.LoadBalancer_APIGateway_AccessToken.load_balancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/get-message")
    public String getMessage() {
        String serviceAUrl = "http://client-service/message";
        return restTemplate.getForObject(serviceAUrl, String.class);
    }
}
