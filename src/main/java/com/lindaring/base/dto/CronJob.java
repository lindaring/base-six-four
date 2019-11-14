package com.lindaring.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CronJob {
  private String expression;
  private String description;
}
