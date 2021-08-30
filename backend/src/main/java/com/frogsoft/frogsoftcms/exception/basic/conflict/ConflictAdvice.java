package com.frogsoft.frogsoftcms.exception.basic.conflict;


import com.frogsoft.frogsoftcms.exception.basic.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConflictAdvice {

  @ResponseBody
  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ExceptionResponseBody resourceConflictAdvice(ConflictException ex) {
    return new ExceptionResponseBody(
        HttpStatus.CONFLICT.value(),
        ex.getMessage(),
        "Resource Conflict"
    );
  }
}
