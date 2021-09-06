package com.frogsoft.frogsoftcms.service.mail;


import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import org.springframework.hateoas.EntityModel;

public interface MailService {

  EntityModel<UserDto> sendCode(String username);

}
