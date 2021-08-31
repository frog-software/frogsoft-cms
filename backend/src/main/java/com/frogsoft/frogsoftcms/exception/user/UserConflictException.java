package com.frogsoft.frogsoftcms.exception.user;

import com.frogsoft.frogsoftcms.exception.basic.conflict.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException(String username) {
    super("用户名 " + username + " 已被使用");
  }
}
