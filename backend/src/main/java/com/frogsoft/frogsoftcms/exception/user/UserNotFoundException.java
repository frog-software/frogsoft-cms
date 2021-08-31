package com.frogsoft.frogsoftcms.exception.user;

import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(String username) {
    super("用户名 " + username + " 不存在");
  }
}
