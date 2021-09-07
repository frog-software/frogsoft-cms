package com.frogsoft.frogsoftcms.dto.mapper.user;

import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.mapper.comment.CommentMapper;
import com.frogsoft.frogsoftcms.dto.model.user.StatisticsDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDetailDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.comment.CommentRepository;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component

public class UserDetailMapper {

  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final ArticleMapper articleMapper;
  private final CommentMapper commentMapper;

  public UserDetailDto toUserDetailDto(User user) {
    AtomicReference<Integer> FavoritesNum = new AtomicReference<>(0);
    for (Article article : user.getFavoriteArticles()) {
      FavoritesNum.set(article.getFavoritesNum() + FavoritesNum.get());
    }
    AtomicReference<Integer> LikesNum = new AtomicReference<>(0);
    for (Article article : user.getLikeArticles()) {
      LikesNum.set(article.getLikesNum() + LikesNum.get());
    }
    AtomicReference<Integer> ViewsNum = new AtomicReference<>(0);
    for (Article article : articleRepository.findByAuthor(user)) {
      ViewsNum.set(article.getViews() + ViewsNum.get());
    }
    StatisticsDto statisticsDto = new StatisticsDto()
        .setFavoritesNum(FavoritesNum.get())
        .setLikesNum(LikesNum.get())
        .setViewsNum(ViewsNum.get())
        .setPublishArticlesNum(articleRepository.findByAuthor(user).size());
    return new UserDetailDto()
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setRoles(user.getRoles())
        .setFavoriteArticles(articleMapper.toArticleDto(user.getFavoriteArticles().subList(0, 9)))
        .setHistoryArticles(articleMapper.toArticleDto(user.getHistoryArticles().subList(0, 9)))
        .setLikeArticles(articleMapper.toArticleDto(user.getLikeArticles().subList(0, 9)))
        .setStatistics(statisticsDto)
        .setPublishArticles(
            articleMapper.toArticleDto(articleRepository.findByAuthor(user).subList(0, 9)))
        .setPublishComment(
            commentMapper.toCommentDto(commentRepository.findByAuthor(user).subList(0, 9)));
  }
}
