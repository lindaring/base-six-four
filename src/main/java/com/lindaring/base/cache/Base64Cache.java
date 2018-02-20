package com.lindaring.base.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Base64Cache {

    private final String base64 = "BASE_64_CACHE";

    @CachePut(value = base64, key = "#key")
    public String cacheBase(String key, String value) {
        return value;
    }

    @Cacheable(value = base64, key = "#key")
    public String getCache(String key) {
        return "";
    }

    @CacheEvict(value = base64, key = "#key")
    public void removeCache(String key) {
        return;
    }

    @CacheEvict(value = base64, allEntries = true)
    public void removeAll () {
        return;
    }
}
