package com.frogsoft.frogsoftcms.dto.model.comment;

import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.commment.Status;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentDto {

  private Long id;
  private UserDto author;
  private ArticleDto article;
  private Status status;
  private String content;
  private LocalDateTime localDateTime;
  private Integer likes;
  private Long parent;
}
