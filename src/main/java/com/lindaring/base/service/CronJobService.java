package com.lindaring.base.service;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.model.CronJob;
import com.lindaring.base.model.CronJobExpression;
import com.lindaring.base.model.CronJobGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.cronutils.model.CronType.QUARTZ;
import static com.lindaring.base.utils.GeneralUtils.stringEmpty;

@Service
public class CronJobService {

  private static final Logger log = LoggerFactory.getLogger(CronJobService.class);

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

  public CronJobGenerated createCronJob(CronJobExpression cronJob) {
    StringBuilder expression = new StringBuilder();
    expression.append(stringEmpty(cronJob.getSeconds()) ? "*" : cronJob.getSeconds()).append(" ");
    expression.append(stringEmpty(cronJob.getMinutes()) ? "*" : cronJob.getMinutes());

    expression.append(" * * * * ? *");
    CronJobGenerated generated = new CronJobGenerated(expression.toString());
    return generated;
  }
}
