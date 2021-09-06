package com.frogsoft.frogsoftcms.service.mail;

import static com.frogsoft.frogsoftcms.FrogsoftCmsBackendApplication.verificationCodeStorage;

import com.frogsoft.frogsoftcms.config.VerificationCode;
import com.frogsoft.frogsoftcms.dto.assembler.user.UserModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Component
public class MailServiceImpl implements MailService {

  private final UserRepository userRepository;
  private final UserModelAssembler userModelAssembler;
  private final UserMapper userMapper;
  @Autowired
  TemplateEngine templateEngine;
  @Autowired
  private JavaMailSender mailSender;
  //邮件发件人
  @Value("${spring.mail.username}")
  private String from;

  @Override
  public EntityModel<UserDto> sendCode(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserNotFoundException(username);
    }
    VerificationCode code = verificationCodeStorage.generateCode(user.getId());
    //创建邮件正文
    Context context = new Context();
    context.setVariable("verifyCode", code.getCode());
    //将模块引擎内容解析成html字符串
    String emailContent = templateEngine.process("emailTemplate", context);
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(user.getEmail());
      helper.setSubject("验证码");
      helper.setText(emailContent, true);
      mailSender.send(message);
    } catch (Exception e) {
      throw new NotFoundException("发送简单邮件时发生异常！" + e);
    }
    return userModelAssembler.toModel(userMapper.toUserDto(user));
  }

}

