package com.frogsoft.frogsoftcms.exception.basic.badrequest;

import com.frogsoft.frogsoftcms.exception.basic.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestAdvice {

  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponseBody badRequestAdvice(BadRequestException ex) {
    return new ExceptionResponseBody(
        HttpStatus.BAD_REQUEST.value(),
        ex.getMessage(),
        "Bad Request"
    );
  }
}
