package com.frogsoft.frogsoftcms.exception.comment;

import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

  public CommentNotFoundException(Long id) {
    super("评论 " + id + " 不存在");
  }
}
