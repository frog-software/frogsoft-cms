package com.frogsoft.frogsoftcms.service.mail;

import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  TemplateEngine templateEngine;
  @Autowired
  private JavaMailSender mailSender;
  //邮件发件人
  @Value("${spring.mail.username}")
  private String from;

  @Override
  public void sendMail(String to, String subject, String verifyCode) {

    //创建邮件正文
    Context context = new Context();
    context.setVariable("verifyCode", verifyCode);
    //将模块引擎内容解析成html字符串
    String emailContent = templateEngine.process("emailTemplate", context);
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(emailContent, true);
      mailSender.send(message);
    } catch (Exception e) {
      throw new NotFoundException("发送简单邮件时发生异常！" + e);
    }
  }

}

