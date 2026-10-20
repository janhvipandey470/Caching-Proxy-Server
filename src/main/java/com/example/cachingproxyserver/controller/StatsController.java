package com.example.cachingproxyserver.controller;

import com.example.cachingproxyserver.DTO.StatsDTO;
import com.example.cachingproxyserver.service.CacheService;
import com.example.cachingproxyserver.service.MetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("proxy/stats")
public class StatsController {
    private final MetricsService service;
    private final CacheService cacheService;

    public StatsController(MetricsService service, CacheService cacheService) {
        this.service = service;
        this.cacheService = cacheService;
    }

    @GetMapping
    public StatsDTO stats(){
        StatsDTO DTO=new StatsDTO();
        DTO.setCacheHits(service.getCacheHits());
        DTO.setCacheMisses(service.getCacheMisses());
        DTO.setTotalRequests(service.getTotalRequests());
        DTO.setCacheSize(cacheService.size());
        DTO.setHitRate(service.getHitRate());
        return DTO;

    }
}
