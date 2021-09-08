package com.frogsoft.frogsoftcms.controller.v1.request.config;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FooterRequest implements Serializable {

  private static final long serialVersionUID = 32974805623333724L;
  private String logo;
}
