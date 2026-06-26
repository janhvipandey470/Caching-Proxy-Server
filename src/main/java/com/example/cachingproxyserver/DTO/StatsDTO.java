package com.example.cachingproxyserver.DTO;

import lombok.Data;

@Data
public class StatsDTO {
    private long totalRequests;
    private long cacheHits;
    private long cacheMisses;
    private double hitRate;
    private int cacheSize;
}
