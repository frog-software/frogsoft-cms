package com.frogsoft.frogsoftcms.dto.model.user;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDetailDto {
  private String email;
  private String username;
  private List<String> roles;
  private Set<Article> favoriteArticles;
  private Set<Article> likeArticles;
  private Set<Article> historyArticles;
  private Set<Article> publishArticles;
  private List<Comment> publishComment;
  private Integer publishArticlesNum;
  private Integer viewsNum;
  private Integer likesNum;
  private Integer favoritesNum;
}
