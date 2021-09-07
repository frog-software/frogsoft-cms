package com.frogsoft.frogsoftcms.service.user;

import static com.frogsoft.frogsoftcms.FrogsoftCmsBackendApplication.verificationCodeStorage;

import com.frogsoft.frogsoftcms.controller.v1.request.User.UserChangePasswordRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRegisterRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRequest;
import com.frogsoft.frogsoftcms.dto.assembler.user.UserModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.exception.basic.conflict.ConflictException;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.unauthorized.UnauthorizedException;
import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.Roles;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserModelAssembler userModelAssembler;
  private final PagedResourcesAssembler<UserDto> pagedResourcesAssembler;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;


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

  @Override
  public EntityModel<UserDto> registerUser(UserRegisterRequest userRegisterRequest) {

    String username = userRegisterRequest.getUsername();
    User user = userRepository.findByUsername(username);
    List<String> role = new LinkedList<>();
    role.add(Roles.ROLE_USER.getRole());

    if (user != null) {
      throw new ConflictException("用户名已存在");
    }
    if (verificationCodeStorage
        .verifyCode(userRegisterRequest.getEmail(), userRegisterRequest.getCode()) != null) {
      User user1 = userRepository.save(new User()
          .setEmail(userRegisterRequest.getEmail())
          .setRoles(role)
          .setUsername(userRegisterRequest.getUsername())
          .setPassword(passwordEncoder.encode(userRegisterRequest.getPassword())));
      return userModelAssembler.toModel(userMapper.toUserDto(user1));
    } else {
      throw new NotFoundException("验证码错误");
    }
  }

  @Override
  public EntityModel<UserDto> resetEmail(String username, String newEmail, String code) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserNotFoundException(username);
    }
    if (verificationCodeStorage.verifyCode(user.getEmail(), code) != null) {
      User newUser = userRepository.save(user.setEmail(newEmail));
      return userModelAssembler.toModel(userMapper.toUserDto(newUser));
    } else {
      throw new NotFoundException("验证码错误");
    }
  }

  @Override
  public EntityModel<UserDto> changePassword(String username,
      UserChangePasswordRequest changePasswordRequest,
      User authenticatedUser) {
    if (!authenticatedUser.getUsername().equals(username)) {
      throw new UnauthorizedException("身份验证不一致，无法修改密码");
    }
    User oldUser = userRepository.findByUsername(username);
    if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), oldUser.getPassword())) {
      throw new UnauthorizedException("旧密码错误");
    }
    oldUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
    User newUser = userRepository.save(oldUser);

    return userModelAssembler.toModel(userMapper.toUserDto(newUser));
  }

  @Override
  public EntityModel<UserDto> alterUserInformation(String oldUserName,
      UserRequest userRequest, User authenticatedUser) {
    if (!authenticatedUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())) {
      if (!authenticatedUser.getUsername().equals(oldUserName)) {
        throw new UnauthorizedException("身份验证不一致，无法修改信息");
      }
    }
    User oldUser = userRepository.findByUsername(oldUserName);
    User newUser = userRepository.findByUsername(userRequest.getUsername());
    if (authenticatedUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())){
      oldUser.setRoles(userRequest.getRoles());
      if (!oldUserName.equals(userRequest.getUsername())){
        if (newUser != null) {
          throw new ConflictException("用户名已存在");
        }
      }
    }
    else {
      if (newUser != null) {
        throw new ConflictException("用户名已存在");
      }
    }
    oldUser.setUsername(userRequest.getUsername());
    User newUser1 = userRepository.save(oldUser);
    return userModelAssembler.toModel(userMapper.toUserDto(newUser1));
  }

  @Override
  @Transactional
  public void deleteUser(String username, User authenticatedUser) {
    if (!authenticatedUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())) {

      if (!authenticatedUser.getUsername().equals(username)) {
        throw new UnauthorizedException("身份验证不一致，无法删除用户");
      }
    }
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserNotFoundException(username);
    }
    userRepository.delete(user);
  }
}
