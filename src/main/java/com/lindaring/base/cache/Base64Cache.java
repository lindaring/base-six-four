package com.lindaring.base.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Base64Cache {

    @CachePut(value = "BASE_64_CACHE", key = "#key")
    public String cacheBase(String key, String value) {
        return value;
    }

    @Cacheable(value = "BASE_64_CACHE", key = "#key")
    public String getCache(String key) {
        return "";
    }

    @CacheEvict(value = "BASE_64_CACHE", key = "#key")
    public void removeCache(String key) {
        return;
    }

    @CacheEvict(value = "BASE_64_CACHE", allEntries = true)
    public void removeAll () {
        return;
    }
}
