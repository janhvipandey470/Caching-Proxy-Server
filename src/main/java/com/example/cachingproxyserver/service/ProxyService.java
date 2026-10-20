package com.example.cachingproxyserver.service;

import com.example.cachingproxyserver.cache.CacheResponse;
import com.example.cachingproxyserver.client.client;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProxyService {
    private static final Logger logger =
            LoggerFactory.getLogger(ProxyService.class);
    private final client client;
    private final CacheService cacheService;
    private final MetricsService metricsService;
    public ProxyService(client client, CacheService cacheService, MetricsService metricsService) {
        this.client = client;
        this.cacheService = cacheService;
        this.metricsService = metricsService;
    }
    private void validateUrl(String url) {

        try {
            new java.net.URI(url).toURL();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
    }
    public String get(String url){
        validateUrl(url);
        logger.info("Cache request for url : {}",url);
        CacheResponse response= cacheService.get(url);
        metricsService.recordRequest();
        if(response==null){
            String result= client.fetch(url);
            CacheResponse response1=new CacheResponse(result);
            cacheService.put(url,response1);
            metricsService.recordMiss();
            logger.info("Cache Miss for url : {}",url);
            System.out.println("Cache miss");
            return response1.getBody();

        }
        else{
            metricsService.recordHit();
            logger.info("Cache Hit for url : {}",url);
            return response.getBody();
        }
    }
}
