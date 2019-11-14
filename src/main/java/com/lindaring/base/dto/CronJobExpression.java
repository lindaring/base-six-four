package com.lindaring.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CronJobExpression {
  private String seconds;
  private String minutes;
  private String hour;
}
