package com.lindaring.base.controller;

import com.lindaring.base.enumerator.CronFrequency;
import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.model.CronJob;
import com.lindaring.base.model.CronJobExpression;
import com.lindaring.base.model.CronJobGenerated;
import com.lindaring.base.service.CronJobService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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

  @ApiOperation(notes = "Convert cron to a human understandable format", value = "Convert cron to a human understandable format")
  @PostMapping(value="/parse")
  public ResponseEntity<CronJob> convertCronJob(@RequestBody CronJob cronJob) throws ParamsException {
    CronJob response = cronJobService.getCronJobDescription(cronJob);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @ApiOperation(notes = "Generate cron job from UI", value = "Generate cron job from UI")
  @PostMapping(value="{frequency}/generate")
  public ResponseEntity<CronJobGenerated> generateCronJob(@PathVariable CronFrequency frequency,
                                                          @RequestBody CronJobExpression cronJobExpression) {
    CronJobGenerated response = cronJobService.createCronJob(frequency, cronJobExpression);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
