package com.lindaring.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CronJob {
  private String expression;
  private String description;
}