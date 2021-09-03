package com.frogsoft.frogsoftcms.service.comment;

import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.dto.assembler.comment.CommentModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.comment.CommentMapper;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.exception.article.ArticleNotFoundException;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import com.frogsoft.frogsoftcms.model.commment.Status;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.comment.CommentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final CommentModelAssembler commentModelAssembler;
  private final ArticleRepository articleRepository;

  @Override
  public EntityModel<CommentDto> saveComment(Long articleId, CommentRequest commentRequest,
      User authenticatedUser) {
    Comment comment = new Comment()
        .setStatus(Status.Normal)
        .setArticle(articleRepository.getById(articleId))
        .setContent(commentRequest.getContent())
        .setAuthor(authenticatedUser)
        .setPublishDate(LocalDateTime.now())
        .setLikes(0);
    if (commentRequest.getParent() != 0L) {
      comment.setParent(commentRepository.getById(commentRequest.getParent()));
    }
    comment = commentRepository.save(comment);
    return commentModelAssembler.toModel(commentMapper.toCommentDto(comment));
  }

  @Override
  public CollectionModel<EntityModel<CommentDto>> getComment(Long id, User authenticateUser) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    List<Comment> commentList = commentRepository.findByArticle(article);
    return commentModelAssembler.toCollectionModel(commentMapper.toCommentDto(commentList));
  }

  @Override
  public EntityModel<CommentDto> get(Long commentId, User authenticatedUser) {
    Comment comment = commentRepository.getById(commentId);
    return commentModelAssembler.toModel(commentMapper.toCommentDto(comment));
  }

  @Override
  public EntityModel<CommentDto> changeContent(Long commentId, String content,
      User authenticatedUser) {
    Comment comment = commentRepository.save(
        commentRepository.getById(commentId).setContent(content)
    );
    return commentModelAssembler.toModel(commentMapper.toCommentDto(comment));
  }
}
