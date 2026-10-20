package com.example.cachingproxyserver.config;

import com.example.cachingproxyserver.cache.CacheResponse;
import com.example.cachingproxyserver.cache.LFUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Value("${cache.capacity}")
    private int capacity;
    @Bean
    public LFUCache<String, CacheResponse>LFUcache(){
        return new LFUCache<>(capacity);
    }
}
