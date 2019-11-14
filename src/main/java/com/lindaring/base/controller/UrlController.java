package com.lindaring.base.controller;

import com.lindaring.base.dto.UrlShortenRequest;
import com.lindaring.base.dto.UrlShortenResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/url")
public class UrlController {

    @PostMapping(value="/shorten")
    public ResponseEntity<UrlShortenResult> shortenUrl(@RequestBody UrlShortenRequest request) {
        return new ResponseEntity<>(new UrlShortenResult(), HttpStatus.OK);
    }

}
