package com.frogsoft.frogsoftcms.dto.mapper.comment;

import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentMapper {

  private final ArticleMapper articleMapper;
  private final UserMapper userMapper;

  public CommentDto toCommentDto(Comment comment) {
    return new CommentDto()
        .setArticle(articleMapper.toArticleDto(comment.getArticle()))
        .setAuthor(userMapper.toUserDto(comment.getAuthor()))
        .setId(comment.getId())
        .setLikes(comment.getLikes())
        .setStatus(comment.getStatus())
        .setLocalDateTime(comment.getPublishDate())
        .setParent(comment.getParent().getId());
  }
}
