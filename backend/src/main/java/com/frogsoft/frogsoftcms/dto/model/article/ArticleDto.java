package com.frogsoft.frogsoftcms.dto.model.article;

import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.article.Status;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArticleDto {

  private Long id;
  private UserDto author;
  private Status status;
  private LocalDateTime publishDate;
  private LocalDateTime updateDate;
  private Integer views;
  private String title;
  private String description;
  private String content;
  private String cover;
}
