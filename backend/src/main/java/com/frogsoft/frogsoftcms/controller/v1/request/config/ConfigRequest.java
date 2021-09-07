package com.frogsoft.frogsoftcms.controller.v1.request.config;

import java.io.Serializable;
import lombok.Data;

@Data
public class ConfigRequest implements Serializable {
  private static final long serialVersionUID = 32974805623333756L;
  private String favicon;
  private String title;
  private String logo;
  private EmailRequest email;
  private HeaderRequest header;
  private FooterRequest footer;
}
