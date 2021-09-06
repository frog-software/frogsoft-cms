package com.frogsoft.frogsoftcms.controller.v1.request.auth;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResetRequest implements Serializable {

  private static final long serialVersionUID = 32974805623333756L;
  private String username;
  private String varyficationcode;
  private String newpassword;
}
