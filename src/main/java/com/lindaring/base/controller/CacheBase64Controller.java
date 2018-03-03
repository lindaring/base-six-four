package com.lindaring.base.controller;

import com.lindaring.base.cache.Base64Cache;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/cache")
public class CacheBase64Controller {

    @Autowired
    private Base64Cache cache;

    @ApiOperation(notes = "Get value from cache", value = "Get value from cache")
    @RequestMapping(value = "/cache/{key}", method = RequestMethod.GET)
    public String getCache(
            @ApiParam(value = "Cache key", required = true) @PathVariable String key) {
        return cache.getCache(key);
    }

    @ApiOperation(notes = "Remove cache key and value", value = "Remove cache key and value")
    @RequestMapping(value = "/cache/{key}", method = RequestMethod.DELETE)
    public String deleteCache(
            @ApiParam(value = "Cache key", required = true) @PathVariable String key) {
        cache.removeCache(key);
        return "Cache deleted!";
    }

    @ApiOperation(notes = "Remove all cache keys and values", value = "Remove all cache keys and values")
    @RequestMapping(value = "/cache", method = RequestMethod.DELETE)
    public String deleteAllCache() {
        cache.removeAll();
        return "Cache deleted!";
    }

}
