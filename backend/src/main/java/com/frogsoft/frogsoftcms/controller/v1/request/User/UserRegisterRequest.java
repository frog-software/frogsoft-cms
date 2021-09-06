package com.frogsoft.frogsoftcms.controller.v1.request.User;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserRegisterRequest implements Serializable {
  private static final long serialVersionUID = 32974805623333756L;
  private String username;
  private String password;
  private String email;
}
