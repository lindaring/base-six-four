package com.lindaring.base.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/secure/v1/dashboard")
public class DashboardController {

    @GetMapping(value = "/health")
    @ApiOperation(notes = "Gets the current system health", value = "Gets the current system health")
    public ResponseEntity<String> getSystemHealth() {
        return new ResponseEntity<>("Health is good!", HttpStatus.OK);
    }

}
