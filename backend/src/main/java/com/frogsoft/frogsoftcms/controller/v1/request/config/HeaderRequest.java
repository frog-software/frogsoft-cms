package com.frogsoft.frogsoftcms.controller.v1.request.config;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HeaderRequest implements Serializable {

  private static final long serialVersionUID = 32974845623333756L;
  private String logo;
}
