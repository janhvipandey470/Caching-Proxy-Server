package com.example.cachingproxyserver.controller;

import com.example.cachingproxyserver.service.ProxyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final ProxyService service;

    public ProxyController(ProxyService service) {
        this.service = service;
    }

    @GetMapping
    public String proxy(String url){
        return service.get(url);
    }
}
