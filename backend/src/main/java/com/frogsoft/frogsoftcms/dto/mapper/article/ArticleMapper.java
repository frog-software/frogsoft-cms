package com.frogsoft.frogsoftcms.dto.mapper.article;

import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import java.util.LinkedList;
import java.util.List;
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
        .setTitle(article.getTitle())
        .setViews(article.getViews())
        .setLikes(article.getLikesNum())
        .setFavorites(article.getFavoritesNum());
  }

  public List<ArticleDto> toArticleDto(List<Article> articleList){
    List<ArticleDto> articleDtoList = new LinkedList<>();
    for (Article article:articleList){
      ArticleDto articleDto =new ArticleDto()
          .setId(article.getId())
          .setContent(article.getContent())
          .setAuthor(userMapper.toUserDto(article.getAuthor()))
          .setCover(article.getCover())
          .setDescription(article.getDescription())
          .setPublishDate(article.getPublishDate())
          .setUpdateDate(article.getUpdateDate())
          .setStatus(article.getStatus())
          .setTitle(article.getTitle())
          .setViews(article.getViews())
          .setLikes(article.getLikesNum())
          .setFavorites(article.getFavoritesNum());
      articleDtoList.add(articleDto);
    }
    return articleDtoList;
  }
}
