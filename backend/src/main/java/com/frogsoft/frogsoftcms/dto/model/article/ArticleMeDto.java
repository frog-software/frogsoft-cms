package com.frogsoft.frogsoftcms.dto.model.article;

import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.article.Status;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArticleMeDto {

  private Long id;
  private UserDto author;
  private Status status;
  private LocalDateTime publishDate;
  private LocalDateTime updateDate;
  private Integer views;
  private boolean liked;
  private boolean favorited;
  private boolean Authored;
  private int likes;
  private int favorites;
  private String title;
  private String description;
  private String content;
  private String cover;
}