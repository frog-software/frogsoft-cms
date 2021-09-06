package com.frogsoft.frogsoftcms.service.auth;

import static com.frogsoft.frogsoftcms.FrogsoftCmsBackendApplication.verificationCodeStorage;

import com.frogsoft.frogsoftcms.config.VerificationCode;
import com.frogsoft.frogsoftcms.dto.assembler.user.UserModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import com.frogsoft.frogsoftcms.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserModelAssembler userModelAssembler;
  private final UserMapper userMapper;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;

  public EntityModel<UserDto> getCode(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserNotFoundException(username);
    }
    VerificationCode code = verificationCodeStorage.generateCode(user.getId());
    mailService.sendMail(user.getEmail(), "验证码", code.getCode());
    return userModelAssembler.toModel(userMapper.toUserDto(user));
  }

  public EntityModel<UserDto> resetPassword(String username, String code, String newPassword) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserNotFoundException(username);
    }
    if (verificationCodeStorage.verifyCode(user.getId(), code) != null) {
      User newUser = userRepository.save(user.setPassword(passwordEncoder.encode(newPassword)));
      return userModelAssembler.toModel(userMapper.toUserDto(newUser));
    } else {
      throw new NotFoundException("验证码错误");
    }
  }


}
