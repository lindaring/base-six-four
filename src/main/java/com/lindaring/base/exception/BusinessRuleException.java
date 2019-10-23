package com.lindaring.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class BusinessRuleException {
  private String message;
  private HttpStatus status;
  private int code;
  private String type;
}
