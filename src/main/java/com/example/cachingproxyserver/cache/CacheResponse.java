package com.example.cachingproxyserver.cache;

import lombok.Data;

@Data
public class CacheResponse {
    String body;
    Long time;
    public CacheResponse(String body){
        this.body=body;
        this.time=System.currentTimeMillis();
    }
}
