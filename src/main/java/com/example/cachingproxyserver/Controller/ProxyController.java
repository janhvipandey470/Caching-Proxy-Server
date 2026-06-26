package com.example.cachingproxyserver.Controller;

import com.example.cachingproxyserver.Service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
