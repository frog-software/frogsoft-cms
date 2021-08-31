package com.frogsoft.frogsoftcms.service.user;

import com.frogsoft.frogsoftcms.dto.mapper.UserModelAssembler;
import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserModelAssembler userModelAssembler;
  private final PagedResourcesAssembler<User> pagedResourcesAssembler;


  @Override
  public PagedModel<EntityModel<User>> getAllUsers(Pageable pageable) {
    Page<User> users = userRepository.findAllBy(pageable);
    return pagedResourcesAssembler.toModel(users, userModelAssembler);
  }

  @Override
  public EntityModel<User> getOneUser(String username) {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UserNotFoundException(username);
    }

    return userModelAssembler.toModel(user);
  }

}
