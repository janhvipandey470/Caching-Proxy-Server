package com.example.cachingproxyserver.Service;

import com.example.cachingproxyserver.cache.CacheResponse;
import com.example.cachingproxyserver.cache.LFUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CacheService {
    private static final Logger logger =
            LoggerFactory.getLogger(CacheService.class);
    @Value("${cache.ttl}")
    private int ttl;
    private final LFUCache<String, CacheResponse> cache;

    public CacheService(LFUCache<String, CacheResponse> cache) {
        this.cache = cache;
    }
    public void put(String key,CacheResponse value){
        cache.put(key, value);
    }
    public CacheResponse get(String key){
        CacheResponse response=cache.get(key);
        if(response==null)return null;
        else{
            if(System.currentTimeMillis()- response.getTime()>=ttl){
                logger.info("Cache entry expire for : {}",key);
                cache.remove(key);
                return null;
            }
            return response;
        }
    }

    public int size() {
        return cache.getCurrentSize();
    }
}
