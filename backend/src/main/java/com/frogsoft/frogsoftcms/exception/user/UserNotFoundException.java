package com.frogsoft.frogsoftcms.exception.user;

import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(String email) {
    super("邮箱 " + email + " 不存在");
  }
}
