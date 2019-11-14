package com.lindaring.base.controller;

import com.lindaring.base.cache.Base64Cache;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/cache")
public class CacheBase64Controller {

    @Autowired
    private Base64Cache cache;

    @ApiOperation(notes = "Get value from cache", value = "Get value from cache")
    @GetMapping(value = "/cache/{key}")
    public String getCache(
            @ApiParam(value = "Cache key", required = true) @PathVariable String key) {
        return cache.getCache(key);
    }

    @ApiOperation(notes = "Remove cache key and value", value = "Remove cache key and value")
    @DeleteMapping(value = "/cache/{key}")
    public String deleteCache(
            @ApiParam(value = "Cache key", required = true) @PathVariable String key) {
        cache.removeCache(key);
        return "Cache deleted!";
    }

    @ApiOperation(notes = "Remove all cache keys and values", value = "Remove all cache keys and values")
    @DeleteMapping(value = "/cache")
    public String deleteAllCache() {
        cache.removeAll();
        return "Cache deleted!";
    }

}
