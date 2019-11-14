package com.lindaring.base.controller;

import com.lindaring.base.dto.Base64;
import com.lindaring.base.enumerator.Charset;
import com.lindaring.base.service.Base64Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.InvalidNameException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/base64")
public class Base64Controller {

    @Autowired
    private Base64Service baseService;

    @ApiOperation(notes = "Base 64 encoded string", value = "Base 64 encoded string")
    @GetMapping(value = "/encode/{string}")
    public Base64 encodeString(
            @ApiParam(value = "String to encode ", required = true) @PathVariable String string,
            @ApiParam(value = "Output charset", required = true) @RequestParam Charset charset)
            throws InvalidNameException, UnsupportedEncodingException {
        return baseService.getEncodedBase64(string, charset);
    }

    @ApiOperation(notes = "Base 64 decode string", value = "Base 64 decode string")
    @GetMapping(value = "/decode/{string}")
    public Base64 decodeString(
            @ApiParam(value = "String to decode", required = true) @PathVariable String string,
            @ApiParam(value = "Output charset", required = true) @RequestParam Charset charset)
            throws InvalidNameException, UnsupportedEncodingException {
        return baseService.getDecodedBase64(string, charset);
    }

    @ApiOperation(notes = "Retrieve list of charset keys", value = "Retrieve list of charset keys")
    @GetMapping(value = "/charset/list")
    public List getCharsetKeys() {
        return baseService.getCharsets();
    }

}
