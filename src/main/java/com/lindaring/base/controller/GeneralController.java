package com.lindaring.base.controller;

import com.lindaring.base.service.GeneralService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/general")
public class GeneralController {

    @Autowired
    private GeneralService generalService;

    @ApiOperation(notes = "Retrieve the current year", value = "Retrieve the current year")
    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public int getCurrentYear() {
        return generalService.getCurrentYear();
    }

}
