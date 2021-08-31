package com.frogsoft.frogsoftcms.exception.basic.unauthorized;

import com.frogsoft.frogsoftcms.exception.basic.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnauthorizedAdvice {

  @ResponseBody
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ExceptionResponseBody unauthorizedAdvice(UnauthorizedException ex) {
    return new ExceptionResponseBody(
        HttpStatus.UNAUTHORIZED.value(),
        ex.getMessage(),
        "Unauthorized"
    );
  }

}
