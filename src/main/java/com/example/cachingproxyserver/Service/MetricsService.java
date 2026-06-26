package com.example.cachingproxyserver.Service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Data
public class MetricsService {
    AtomicLong totalRequest=new AtomicLong(0);
    AtomicLong cacheHit=new AtomicLong(0);
    AtomicLong cacheMiss=new AtomicLong(0);
    public void recordHit(){
        this.cacheHit.incrementAndGet();
    }
    public void recordMiss(){
        this.cacheMiss.incrementAndGet();
    }
    public void recordRequest() {
        this.totalRequest.incrementAndGet();
    }

    public long getTotalRequests() {
        return totalRequest.get();
    }

    public long getCacheHits() {
        return cacheHit.get();
    }

    public long getCacheMisses() {
        return cacheMiss.get();
    }

    public double getHitRate() {

        long requests = totalRequest.get();

        if (requests == 0) {
            return 0.0;
        }

        double rate = (cacheHit.get() * 100.0) / requests;

        return Math.round(rate * 100.0) / 100.0;
    }
}
