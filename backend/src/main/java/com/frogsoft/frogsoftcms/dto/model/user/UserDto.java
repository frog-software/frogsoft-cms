package com.frogsoft.frogsoftcms.dto.model.user;


import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {

  private String email;
  private String username;
  private List<String> roles;
}
