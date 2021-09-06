package com.frogsoft.frogsoftcms.controller.v1.request.User;

import lombok.Data;

@Data
public class UserChangePasswordRequest {
  private String username;
  private String oldPassword;
  private String newPassword;
}
