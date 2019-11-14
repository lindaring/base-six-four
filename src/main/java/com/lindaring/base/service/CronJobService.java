package com.lindaring.base.service;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.lindaring.base.dto.CronJob;
import com.lindaring.base.dto.CronJobExpression;
import com.lindaring.base.dto.CronJobGenerated;
import com.lindaring.base.enumerator.CronFrequency;
import com.lindaring.base.exception.ParamsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.cronutils.model.CronType.QUARTZ;
import static com.lindaring.base.utils.GeneralUtils.stringEmpty;

@Slf4j
@Service
public class CronJobService {

  private static final String DEFAULT_CRON = "* * * * * ? *";

  public CronJob getCronJobDescription(CronJob cronJob) throws ParamsException {
    if (stringEmpty(cronJob.getExpression()))
      throw new ParamsException("Provide the cron job expression");

    CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
    CronParser parser = new CronParser(cronDefinition);

    CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);
    try {
      Cron parsedCron = parser.parse(cronJob.getExpression());
      String description = descriptor.describe(parsedCron);
      return new CronJob(cronJob.getExpression(), description);
    } catch (IllegalArgumentException e) {
      throw new ParamsException(e.getMessage());
    }
  }

  public CronJobGenerated createCronJob(CronFrequency frequency, CronJobExpression cronJob) {
    String expression = "";

    if (frequency == CronFrequency.SECONDS && !stringEmpty(cronJob.getSeconds()))
      expression = createSecondsCronJob(cronJob);
    else if (frequency == CronFrequency.MINUTES && !stringEmpty(cronJob.getMinutes()))
      expression = createMinutesCronJob(cronJob);
    else if (frequency == CronFrequency.HOURLY && !stringEmpty(cronJob.getHour()))
      expression = createHourCronJob(cronJob);
    else
      expression = DEFAULT_CRON;

    CronJobGenerated generated = new CronJobGenerated(expression);
    return generated;
  }

  private String createSecondsCronJob(CronJobExpression cronJob) {
    return ("0/" + cronJob.getSeconds()) + " * * * * ? *";
  }

  private String createMinutesCronJob(CronJobExpression cronJob) {
    return "0 " + ("0/" + cronJob.getMinutes()) + " * * * ? *";
  }

  private String createHourCronJob(CronJobExpression cronJob) {
    return "0 0 " + ("0/" + cronJob.getHour()) + " * * ? *";
  }

}
