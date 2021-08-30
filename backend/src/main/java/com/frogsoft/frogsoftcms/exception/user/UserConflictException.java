package com.frogsoft.frogsoftcms.exception.user;

import com.frogsoft.frogsoftcms.exception.basic.conflict.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException(String email) {
    super("邮箱 " + email + " 已被使用");
  }
}
