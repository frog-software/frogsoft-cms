package com.frogsoft.frogsoftcms.service.user;

import static com.frogsoft.frogsoftcms.FrogsoftCmsBackendApplication.verificationCodeStorage;

import com.frogsoft.frogsoftcms.controller.v1.request.User.UserChangePasswordRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRegisterRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.User.UserRequest;
import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.assembler.comment.CommentModelAssembler;
import com.frogsoft.frogsoftcms.dto.assembler.user.UserModelAssembler;
import com.frogsoft.frogsoftcms.dto.history.HistoryModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.mapper.comment.CommentMapper;
import com.frogsoft.frogsoftcms.dto.mapper.history.HistoryMapper;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserDetailMapper;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.dto.model.history.HistoryDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDetailDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.exception.basic.conflict.ConflictException;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.unauthorized.UnauthorizedException;
import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.Roles;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.comment.CommentRepository;
import com.frogsoft.frogsoftcms.repository.history.HistoryRepository;
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
  private final HistoryRepository historyRepository;
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final UserModelAssembler userModelAssembler;
  private final HistoryModelAssembler historyModelAssembler;
  private final ArticleModelAssembler articleModelAssembler;
  private final CommentModelAssembler commentModelAssembler;
  private final PagedResourcesAssembler<UserDto> pagedResourcesAssembler;
  private final PagedResourcesAssembler<HistoryDto> historyDtoPagedResourcesAssembler;
  private final PagedResourcesAssembler<ArticleDto> articleDtoPagedResourcesAssembler;
  private final PagedResourcesAssembler<CommentDto> commentDtoPagedResourcesAssembler;
  private final UserMapper userMapper;
  private final UserDetailMapper userDetailMapper;
  private final HistoryMapper historyMapper;
  private final ArticleMapper articleMapper;
  private final CommentMapper commentMapper;
  private final PasswordEncoder passwordEncoder;


  @Override
  public PagedModel<EntityModel<UserDto>> getAllUsers(Pageable pageable) {

    Page<UserDto> users = userRepository.findAllBy(pageable)
        .map(userMapper::toUserDto);

    return pagedResourcesAssembler.toModel(users, userModelAssembler);
  }

  @Override
  public EntityModel<UserDetailDto> getOneUser(String username) {

    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserNotFoundException(username);
    }

    return userModelAssembler.toDetailModel(userDetailMapper.toUserDetailDto(user));
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
          .setAvatar("https://dummyimage.com/100x100")
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
    User oldUser = userRepository.findByUsername(oldUserName);
    User newUser = userRepository.findByUsername(userRequest.getUsername());
    if (authenticatedUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())) {
      oldUser.setRoles(userRequest.getRoles());
    }
    if (!oldUserName.equals(userRequest.getUsername())) {
      if (newUser != null) {
        throw new ConflictException("用户名已存在");
      }
    }
    oldUser.setUsername(userRequest.getUsername());
    oldUser.setAvatar(userRequest.getAvatar());
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

  @Override
  public PagedModel<EntityModel<HistoryDto>> getUserHistory(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username);
    Page<HistoryDto> historyDtos = historyRepository.findAllByUser(user, pageable).map(
        historyMapper::toHistoryDto
    );
    return historyDtoPagedResourcesAssembler.toModel(historyDtos, historyModelAssembler);
  }

  @Override
  public PagedModel<EntityModel<ArticleDto>> getUserFavor(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username);
    Page<ArticleDto> articleDtos = articleRepository.findByAuthorASCAdmin(user, "id", pageable).map(
        articleMapper::toArticleDto
    );
    return articleDtoPagedResourcesAssembler.toModel(articleDtos, articleModelAssembler);
  }

  @Override
  public PagedModel<EntityModel<CommentDto>> getUserComment(String username, Pageable pageable) {
    User user = userRepository.findByUsername(username);
    Page<CommentDto> commentDtos = commentRepository.findAllByAuthor(user, pageable).map(
        commentMapper::toCommentDto
    );

    return commentDtoPagedResourcesAssembler.toModel(commentDtos, commentModelAssembler);
  }
}
