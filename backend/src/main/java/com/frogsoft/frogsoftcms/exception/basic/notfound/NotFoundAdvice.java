package com.frogsoft.frogsoftcms.exception.basic.notfound;

import com.frogsoft.frogsoftcms.exception.basic.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponseBody resourceNotFoundException(NotFoundException ex) {
    return new ExceptionResponseBody(
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage(),
        "Resource Not Found"
    );
  }
}
