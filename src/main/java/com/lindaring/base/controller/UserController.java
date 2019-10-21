package com.lindaring.base.controller;

import com.lindaring.base.model.GeneralResponse;
import com.lindaring.base.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/user")
public class UserController {

    @PostMapping(value="/")
    @ApiOperation(notes = "Record user", value = "Record user")
    public ResponseEntity<GeneralResponse> recordUser(HttpServletRequest httpServletRequest,
                                                      @ApiParam(value="User information", required=true)
                                                      @RequestBody User user) {
        return new ResponseEntity<>(new GeneralResponse(), HttpStatus.CREATED);
    }

}
