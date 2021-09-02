package com.frogsoft.frogsoftcms.dto.mapper.article;

import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ArticleMapper {

  private final UserMapper userMapper;

  public ArticleDto toArticleDto(Article article) {

    return new ArticleDto()
        .setId(article.getId())
        .setContent(article.getContent())
        .setAuthor(userMapper.toUserDto(article.getAuthor()))
        .setCover(article.getCover())
        .setDescription(article.getDescription())
        .setPublishDate(article.getPublishDate())
        .setUpdateDate(article.getUpdateDate())
        .setStatus(article.getStatus())
        .setTitle(article.getTitle());
  }
}
