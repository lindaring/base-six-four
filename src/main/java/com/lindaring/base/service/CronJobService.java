package com.lindaring.base.service;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.model.CronJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.cronutils.model.CronType.QUARTZ;

@Service
public class CronJobService {

  private static final Logger log = LoggerFactory.getLogger(CronJobService.class);

  public CronJob getCronJobDescription(CronJob cronJob) throws ParamsException {
    if (cronJob.getExpression() == null || cronJob.getExpression().equals(""))
      throw new ParamsException("Provide the cron job expression");

    CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
    CronParser parser = new CronParser(cronDefinition);

    CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);
    try {
      String description = descriptor.describe(parser.parse(cronJob.getExpression()));
      return new CronJob(cronJob.getExpression(), description);
    } catch (IllegalArgumentException e) {
      throw new ParamsException(e.getMessage());
    }
  }
}
