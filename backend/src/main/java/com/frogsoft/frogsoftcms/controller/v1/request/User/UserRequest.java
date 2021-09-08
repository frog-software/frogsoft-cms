package com.frogsoft.frogsoftcms.controller.v1.request.User;


import java.util.List;
import lombok.Data;

@Data
public class UserRequest {

  private String email;
  private String username;
  private List<String> roles;
  private String avatar;
}