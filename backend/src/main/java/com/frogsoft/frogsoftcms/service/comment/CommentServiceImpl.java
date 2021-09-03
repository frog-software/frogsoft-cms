package com.frogsoft.frogsoftcms.service.comment;

import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.dto.assembler.comment.CommentModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.comment.CommentMapper;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import com.frogsoft.frogsoftcms.model.commment.Status;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.comment.CommentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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
    Comment comment = commentRepository.save(new Comment()
        .setStatus(Status.Normal)
        .setArticle(articleRepository.getById(articleId))
        .setContent(commentRequest.getContent())
        .setAuthor(authenticatedUser)
        .setPublishDate(LocalDateTime.now())
        .setLikes(0));
    if (!commentRequest.getParent().equals(0)) {
      comment.setParent(commentRepository.getById(commentRequest.getParent()));
    }
    return commentModelAssembler.toModel(commentMapper.toCommentDto(comment));
  }
}
