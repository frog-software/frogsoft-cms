package com.frogsoft.frogsoftcms.dto.mapper.comment;

import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentMapper {

  private final ArticleMapper articleMapper;
  private final UserMapper userMapper;

  public CommentDto toCommentDto(Comment comment) {
    CommentDto commentDto = new CommentDto()
        .setArticle(articleMapper.toArticleDto(comment.getArticle()))
        .setAuthor(userMapper.toUserDto(comment.getAuthor()))
        .setId(comment.getId())
        .setContent(comment.getContent())
        .setParent(0L)
        .setLikes(comment.getLikes())
        .setStatus(comment.getStatus())
        .setPublishDateTime(comment.getPublishDate());
    if (comment.getParent() != null) {
      commentDto.setParent(comment.getParent().getId());
    }
    return commentDto;
  }

  public List<CommentDto> toCommentDto(List<Comment> commentList) {
    List<CommentDto> commentDtoList = new LinkedList<>();
    for (Comment comment : commentList) {
      CommentDto commentDto = new CommentDto()
          .setArticle(articleMapper.toArticleDto(comment.getArticle()))
          .setAuthor(userMapper.toUserDto(comment.getAuthor()))
          .setId(comment.getId())
          .setLikes(comment.getLikes())
          .setStatus(comment.getStatus())
          .setPublishDateTime(comment.getPublishDate());
      if (comment.getParent() != null) {
        commentDto.setParent(comment.getParent().getId());
      }
      commentDtoList.add(commentDto);
    }
    return commentDtoList;
  }
}
