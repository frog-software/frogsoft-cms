package com.frogsoft.frogsoftcms.dto.model.user;

import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDetailDto {

  private String email;
  private String username;
  private List<String> roles;
  private List<ArticleDto> favoriteArticles;
  private List<ArticleDto> likeArticles;
  private List<ArticleDto> historyArticles;
  private List<ArticleDto> publishArticles;
  private List<CommentDto> publishComment;
  private StatisticsDto statistics;
}
