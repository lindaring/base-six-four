package com.lindaring.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ParamsException.class)
  public ResponseEntity<BusinessRuleException> handleMethodArgumentTypeMismatch(ParamsException e, WebRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    BusinessRuleException apiException = new BusinessRuleException(e.getMessage(), status, status.value(), e.getClass().toString());
    return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TechnicalException.class)
  public ResponseEntity<BusinessRuleException> handleMethodUnexpectedErrors(ParamsException e, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    BusinessRuleException apiException = new BusinessRuleException(e.getMessage(), status, status.value(), e.getClass().toString());
    return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
  }

}
