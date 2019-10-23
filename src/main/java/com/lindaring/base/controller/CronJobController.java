package com.lindaring.base.controller;

import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.model.CronJob;
import com.lindaring.base.service.CronJobService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/base-six-four/v1/cronjob")
public class CronJobController {

  @Autowired
  private CronJobService cronJobService;

  @PostMapping(value="/")
  @ApiOperation(notes = "Record user", value = "Record user")
  public ResponseEntity<CronJob> recordUser(@RequestBody CronJob cronJob) throws ParamsException {
    CronJob response = cronJobService.getCronJobDescription(cronJob);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
