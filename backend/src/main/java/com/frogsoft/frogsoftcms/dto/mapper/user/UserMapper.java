package com.frogsoft.frogsoftcms.dto.mapper.user;

import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDto toUserDto(User user) {

    return new UserDto()
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setRoles(user.getRoles())
        .setAvatar(user.getAvatar());
  }
}
