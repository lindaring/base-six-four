package com.lindaring.base.controller;

import com.lindaring.base.service.GeneralService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/general")
public class GeneralController {

    @Autowired
    private GeneralService generalService;

    @GetMapping(value = "/year")
    @ApiOperation(notes = "Retrieve the current year", value = "Retrieve the current year")
    public int getCurrentYear() {
        return generalService.getCurrentYear();
    }

}
