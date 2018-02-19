package com.lindaring.base.controller;

import com.lindaring.base.service.BaseSixFourService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/base64")
public class BaseSixFourController {

    @Autowired
    private BaseSixFourService baseService;

    @ApiOperation(notes = "Convert string to base 64 encoded string", value = "Convert string to base 64 encoded string")
    @RequestMapping(value = "/encode/{string}", method = RequestMethod.GET)
    public String getEncodedString(
            @ApiParam(value = "Return encoded string", required = true) @PathVariable String string)
            throws InvalidNameException {
        return baseService.getEncodedBase64(string);
    }

}
