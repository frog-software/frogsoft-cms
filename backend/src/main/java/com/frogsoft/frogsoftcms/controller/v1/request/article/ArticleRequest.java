package com.frogsoft.frogsoftcms.controller.v1.request.article;

import com.frogsoft.frogsoftcms.model.article.Status;
import java.io.Serializable;
import lombok.Data;

@Data
public class ArticleRequest implements Serializable {

  private static final long serialVersionUID = 32974805623333756L;
  private String title;
  private String description;
  private String content;
  private String cover;
  private Status status;
}
