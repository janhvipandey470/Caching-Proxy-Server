package com.example.cachingproxyserver.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class client {
    private final RestTemplate restTemplate;

    public client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetch(String url){
        return restTemplate.getForObject(url,String.class);
    }
}
