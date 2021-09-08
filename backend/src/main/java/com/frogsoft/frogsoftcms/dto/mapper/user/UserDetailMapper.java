package com.frogsoft.frogsoftcms.dto.mapper.user;

import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.mapper.comment.CommentMapper;
import com.frogsoft.frogsoftcms.dto.mapper.history.HistoryMapper;
import com.frogsoft.frogsoftcms.dto.model.user.StatisticsDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDetailDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.history.History;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.comment.CommentRepository;
import com.frogsoft.frogsoftcms.repository.history.HistoryRepository;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component

public class UserDetailMapper {

  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final HistoryRepository historyRepository;
  private final ArticleMapper articleMapper;
  private final CommentMapper commentMapper;
  private final HistoryMapper historyMapper;

  private static <T> List<T> getFrontList(List<T> list, int num) {
    if (list.size() <= num) {
      return list;
    } else {
      return list.subList(0, num);
    }
  }

  public UserDetailDto toUserDetailDto(User user) {

    int frontListSize = 10;

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

    List<History> historyList = historyRepository.findAllByUser(user);

    StatisticsDto statisticsDto = new StatisticsDto()
        .setFavoritesNum(FavoritesNum.get())
        .setLikesNum(LikesNum.get())
        .setViewsNum(ViewsNum.get())
        .setPublishArticlesNum(articleRepository.findByAuthor(user).size());
    return new UserDetailDto()
        .setEmail(user.getEmail())
        .setAvatar(user.getAvatar())
        .setUsername(user.getUsername())
        .setRoles(user.getRoles())
        .setFavoriteArticles(
            articleMapper.toArticleDto(getFrontList(user.getFavoriteArticles(), frontListSize)))
        .setHistoryArticles(historyMapper.toHistoryDto(getFrontList(historyList, frontListSize)))
        .setLikeArticles(
            articleMapper.toArticleDto(getFrontList(user.getLikeArticles(), frontListSize)))
        .setStatistics(statisticsDto)
        .setPublishArticles(
            articleMapper.toArticleDto(
                getFrontList(articleRepository.findByAuthor(user), frontListSize)))
        .setPublishComment(
            commentMapper.toCommentDto(
                getFrontList(commentRepository.findByAuthor(user), frontListSize)));
  }
}
