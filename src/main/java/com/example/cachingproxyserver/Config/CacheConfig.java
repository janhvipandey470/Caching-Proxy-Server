package com.example.cachingproxyserver.Config;

import com.example.cachingproxyserver.cache.CacheResponse;
import com.example.cachingproxyserver.cache.LFUCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Value("${cache.capacity}")
    private int capacity;
    @Bean
    public LFUCache<String, CacheResponse>LFUcache(){
        return new LFUCache<>(capacity);
    }
}
