package com.frogsoft.frogsoftcms.controller.v1.request.User;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserEmailResetRequest implements Serializable {

  private static final long serialVersionUID = 32974805623333756L;
  private String varyficationcode;
  private String newemail;

}
