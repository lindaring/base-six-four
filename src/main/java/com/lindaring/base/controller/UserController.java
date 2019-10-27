package com.lindaring.base.controller;

import com.lindaring.base.model.GeneralResponse;
import com.lindaring.base.model.User;
import com.lindaring.base.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping(value="/")
    @ApiOperation(notes = "Record user", value = "Record user")
    public ResponseEntity<GeneralResponse> recordUser(HttpServletRequest httpRequest,
                                                      @RequestBody User user) {
        userService.recordUser(httpRequest, user);
        return new ResponseEntity<>(new GeneralResponse(true), HttpStatus.CREATED);
    }

}
