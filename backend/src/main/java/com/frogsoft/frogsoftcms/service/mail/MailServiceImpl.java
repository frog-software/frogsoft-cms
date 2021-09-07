package com.frogsoft.frogsoftcms.service.mail;

import static com.frogsoft.frogsoftcms.FrogsoftCmsBackendApplication.verificationCodeStorage;

import com.frogsoft.frogsoftcms.config.VerificationCode;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.model.config.Config;
import com.frogsoft.frogsoftcms.repository.config.ConfigRepository;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Component
public class MailServiceImpl implements MailService {

  private final ConfigRepository configRepository;
  @Autowired
  TemplateEngine templateEngine;


  private static JavaMailSenderImpl createMailSender(String host, int port, String username,
      String password) {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost(host);
    sender.setPort(port);
    sender.setUsername(username);
    sender.setPassword(password);
    return sender;
  }

  @Override
  public void sendCode(String email) {
    VerificationCode code = verificationCodeStorage.generateCode(email);
    Config email_account = configRepository.findByConfigKey("email_account");
    Config email_password = configRepository.findByConfigKey("email_password");
    Config email_port = configRepository.findByConfigKey("email_port");
    Config email_host = configRepository.findByConfigKey("email_host");
    //创建邮件正文
    Context context = new Context();
    context.setVariable("verifyCode", code.getCode());
    //将模块引擎内容解析成html字符串
    String emailContent = templateEngine.process("emailTemplate", context);
    try {
      JavaMailSenderImpl sender = createMailSender(email_host.getConfigValue(),
          Integer.parseInt(email_port.getConfigValue()),
          email_account.getConfigValue(),
          email_password.getConfigValue());
      // 创建Session实例对象
      Session session = Session.getDefaultInstance(new Properties());
      MimeMessage message = new MimeMessage(session);
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(email_account.getConfigValue());
      helper.setTo(email);
      helper.setSubject("验证码");
      helper.setText(emailContent, true);

      sender.send(message);
    } catch (Exception e) {
      throw new NotFoundException("发送简单邮件时发生异常！" + e);
    }
  }

}

