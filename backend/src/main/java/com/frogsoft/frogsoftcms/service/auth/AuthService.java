package com.frogsoft.frogsoftcms.service.auth;

import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.EntityModel;

public interface AuthService {

  EntityModel<UserDto> resetPassword(String username, String code, String newPassword);

  User findByUsername(String username);
}
