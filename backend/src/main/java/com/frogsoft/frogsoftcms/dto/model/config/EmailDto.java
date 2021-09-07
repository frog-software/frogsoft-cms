package com.frogsoft.frogsoftcms.dto.model.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmailDto {
  private String password;
  private String account;
  private String body;
  private String title;
}
