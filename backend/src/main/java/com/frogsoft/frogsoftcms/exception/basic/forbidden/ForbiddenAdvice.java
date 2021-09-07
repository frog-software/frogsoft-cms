package com.frogsoft.frogsoftcms.exception.basic.forbidden;

import com.frogsoft.frogsoftcms.exception.basic.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ForbiddenAdvice {

  @ResponseBody
  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ExceptionResponseBody forbiddenException(ForbiddenException ex) {
    return new ExceptionResponseBody(
        HttpStatus.FORBIDDEN.value(),
        ex.getMessage(),
        "Forbidden"
    );
  }
}
