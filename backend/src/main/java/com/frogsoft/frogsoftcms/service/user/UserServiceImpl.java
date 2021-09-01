package com.frogsoft.frogsoftcms.service.user;

import com.frogsoft.frogsoftcms.dto.assembler.user.UserModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserModelAssembler userModelAssembler;
  private final PagedResourcesAssembler<UserDto> pagedResourcesAssembler;
  private final UserMapper userMapper;


  @Override
  public PagedModel<EntityModel<UserDto>> getAllUsers(Pageable pageable) {

    Page<UserDto> users = userRepository.findAllBy(pageable)
        .map(userMapper::toUserDto);

    return pagedResourcesAssembler.toModel(users, userModelAssembler);
  }

  @Override
  public EntityModel<UserDto> getOneUser(String username) {

    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UserNotFoundException(username);
    }

    return userModelAssembler.toModel(userMapper.toUserDto(user));
  }

}
