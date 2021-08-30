package com.frogsoft.frogsoftcms.exception.basic;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponseBody {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private int status;
  private String message;
  private String error;
}
