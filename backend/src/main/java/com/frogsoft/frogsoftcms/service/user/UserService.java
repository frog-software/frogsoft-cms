package com.frogsoft.frogsoftcms.service.user;

import com.frogsoft.frogsoftcms.controller.v1.request.User.UserChangePasswordRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRegisterRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRequest;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface UserService {

  PagedModel<EntityModel<UserDto>> getAllUsers(Pageable pageable);

  EntityModel<UserDto> getOneUser(String username);

  EntityModel<UserDto> registerUser(UserRegisterRequest userRegisterRequest);

  EntityModel<UserDto> resetEmail(String username, String newEmail, String code);

  EntityModel<UserDto> changePassword(String username,
      UserChangePasswordRequest changePasswordRequest,
      User authenticatedUser);

  EntityModel<UserDto> alterUserInformation(String username,
      UserRequest userRequest, User authenticatedUser);

  void deleteUser(String username, User authenticatedUser);
}
