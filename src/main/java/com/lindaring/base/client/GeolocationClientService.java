package com.lindaring.base.client;

import com.lindaring.base.client.model.Geolocation;
import com.lindaring.base.properties.GeolocationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GeolocationClientService {

    @Autowired
    private GeolocationProperties geolocationProperties;

    @Autowired
    private GeolocationClient client;

    @FeignClient(
        name = "GeolocationClient",
        url = "${api.geolocation.url}"
    )
    private interface GeolocationClient {
        @GetMapping(value = "/{ip}")
        Geolocation getLocation(@RequestParam("access_key") String apiKey,
                                @PathVariable String ip);
    }

    public Geolocation getLocation(String ip) {
        return client.getLocation(geolocationProperties.getKey(), ip);
    }
}
