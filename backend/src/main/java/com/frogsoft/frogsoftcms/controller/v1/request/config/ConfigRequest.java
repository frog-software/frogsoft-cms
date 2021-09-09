package com.frogsoft.frogsoftcms.controller.v1.request.config;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfigRequest implements Serializable {

  private static final long serialVersionUID = 32974805623373756L;
  private String favicon;
  private String title;
  private String logo;
  private EmailRequest emailDto;
  private HeaderRequest headerDto;
  private FooterRequest footerDto;
}
