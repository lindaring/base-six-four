package com.lindaring.base.controller;

import com.lindaring.base.dto.ActivationRequest;
import com.lindaring.base.dto.GeneralResponse;
import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.dto.Token;
import com.lindaring.base.dto.UserDto;
import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.exception.TechnicalException;
import com.lindaring.base.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/visit")
    @ApiOperation(notes = "Record a page visit", value = "Record a page visit")
    public ResponseEntity<GeneralResponse> recordHit(HttpServletRequest httpRequest, @RequestBody UserDto userDto) {
        userService.recordVisit(httpRequest, userDto);
        return new ResponseEntity<>(new GeneralResponse(true), HttpStatus.CREATED);
    }

    @PostMapping(value = "/register")
    @ApiOperation(notes = "Register user", value = "Register user")
    public ResponseEntity<GeneralResponse> registerUser(@RequestBody RegisteredUser user) throws TechnicalException, ParamsException {
        userService.registerUser(user);
        return new ResponseEntity<>(new GeneralResponse(true), HttpStatus.CREATED);
    }

    @PutMapping(value = "/activate")
    @ApiOperation(notes = "Activate registration", value = "Activate registration")
    public ResponseEntity<GeneralResponse> activateUser(@RequestBody ActivationRequest code) throws TechnicalException, ParamsException {
        userService.activateRegistration(code);
        return new ResponseEntity<>(new GeneralResponse(true), HttpStatus.CREATED);
    }

    @PutMapping(value = "/approve")
    @ApiOperation(notes = "Approval from admin for registration", value = "Approval from admin for registration")
    public ResponseEntity<GeneralResponse> approveRegistration(@RequestBody ActivationRequest code) throws TechnicalException, ParamsException {
        userService.approveRegistration(code);
        return new ResponseEntity<>(new GeneralResponse(true), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    @ApiOperation(notes = "Authenticate user", value = "Authenticate user")
    public ResponseEntity<Token> authenticateUser(@RequestBody RegisteredUser user) throws TechnicalException, ParamsException {
        String token = userService.requestToken(user);
        return new ResponseEntity<>(new Token(token), HttpStatus.CREATED);
    }

}
