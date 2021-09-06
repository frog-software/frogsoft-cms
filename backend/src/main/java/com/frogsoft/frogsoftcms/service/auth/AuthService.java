package com.frogsoft.frogsoftcms.service.auth;

import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import org.springframework.hateoas.EntityModel;

public interface AuthService {

  EntityModel<UserDto> getCode(String username);

  EntityModel<UserDto> resetPassword(String username, String code, String newPassword);
}
