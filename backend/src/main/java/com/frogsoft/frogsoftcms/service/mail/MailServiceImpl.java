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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Component
public class MailServiceImpl implements MailService {

  private final ConfigRepository configRepository;


  private static JavaMailSenderImpl createMailSender(String host, int port, String username,
      String password) {
    Properties properties = new Properties();
    properties.setProperty("mail.smtp.auth", "true");//开启认证
    properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));//设置ssl端口
    properties.setProperty("mail.smtp.socketFactory.fallback", "false");
    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost(host);
    sender.setPort(port);
    sender.setUsername(username);
    sender.setPassword(password);
    sender.setJavaMailProperties(properties);
    return sender;
  }

  @Override
  public void sendCode(String email) {
    VerificationCode code = verificationCodeStorage.generateCode(email);
    Config email_account = configRepository.findByConfigKey("email_account");
    Config email_password = configRepository.findByConfigKey("email_password");
    Config email_port = configRepository.findByConfigKey("email_port");
    Config email_host = configRepository.findByConfigKey("email_host");
    Config email_body = configRepository.findByConfigKey("email_body");
    Config email_title = configRepository.findByConfigKey("email_title");
    //创建邮件正文
    String emailContent = email_body.getConfigValue();
    emailContent = emailContent.replace("[[${verifyCode}]]", code.getCode());
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
      helper.setSubject(email_title.getConfigValue());
      helper.setText(emailContent, true);

      sender.send(message);
    } catch (Exception e) {
      throw new NotFoundException("发送简单邮件时发生异常！" + e);
    }
  }

}

