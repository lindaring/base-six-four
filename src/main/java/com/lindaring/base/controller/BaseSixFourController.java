package com.lindaring.base.controller;

import com.lindaring.base.service.BaseSixFourService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/base64")
public class BaseSixFourController {

    @Autowired
    private BaseSixFourService baseService;

    @ApiOperation(notes = "Base 64 encoded string", value = "Base 64 encoded string")
    @RequestMapping(value = "/encode/{string}", method = RequestMethod.GET)
    public String encodeString(
            @ApiParam(value = "Return encoded string", required = true) @PathVariable String string)
            throws InvalidNameException, UnsupportedEncodingException {
        return baseService.getEncodedBase64(string);
    }

    @ApiOperation(notes = "Base 64 decode string", value = "Base 64 decode string")
    @RequestMapping(value = "/decode/{string}", method = RequestMethod.GET)
    public String decodeString(
            @ApiParam(value = "Return decoded string", required = true) @PathVariable String string)
            throws InvalidNameException, UnsupportedEncodingException {
        return baseService.getDecodedBase64(string);
    }

}
