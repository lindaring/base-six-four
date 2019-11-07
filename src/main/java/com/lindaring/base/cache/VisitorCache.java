package com.lindaring.base.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class VisitorCache {

    private static final String CACHE_NAME = "VISITOR_CACHE";

    @CachePut(value = CACHE_NAME, key = "#key")
    public String cachePut(String key, String value) {
        return value;
    }

    @Cacheable(value = CACHE_NAME, key = "#key")
    public String getCache(String key) {
        return "";
    }

    @CacheEvict(value = CACHE_NAME, key = "#key")
    public void removeCache(String key) {
        return;
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void removeAll () {
        return;
    }
}
