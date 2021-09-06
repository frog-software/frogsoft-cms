package com.frogsoft.frogsoftcms.service.user;

import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRegisterRequest;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {

  PagedModel<EntityModel<UserDto>> getAllUsers(Pageable pageable);

  EntityModel<UserDto> getOneUser(String username);

  EntityModel<UserDto> registerUser(UserRegisterRequest userRegisterRequest);
}
