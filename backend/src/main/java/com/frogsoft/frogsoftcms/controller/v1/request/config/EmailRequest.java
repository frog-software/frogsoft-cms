package com.frogsoft.frogsoftcms.controller.v1.request.config;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmailRequest implements Serializable {

  private static final long serialVersionUID = 32974805625333756L;
  private String password;
  private String account;
  private String body;
  private String title;
  private String host;
  private String port;
}
