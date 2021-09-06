package com.frogsoft.frogsoftcms.exception.article;

import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {

  public ArticleNotFoundException(Long id) {
    super("文章 " + id + " 不存在");
  }
}
