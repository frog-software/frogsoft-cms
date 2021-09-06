package com.frogsoft.frogsoftcms.controller.v1.request.comment;

import java.io.Serializable;
import lombok.Data;

@Data
public class CommentRequest implements Serializable {

  private static final long serialVersionUID = 32974805623333756L;
  private String content;
  private Long parent;
}
