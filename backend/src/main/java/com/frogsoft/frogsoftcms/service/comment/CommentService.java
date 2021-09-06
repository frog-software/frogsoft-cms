package com.frogsoft.frogsoftcms.service.comment;

import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface CommentService {

  EntityModel<CommentDto> saveComment(Long articleId, CommentRequest commentRequest,
      User authenticatedUser);

  CollectionModel<EntityModel<CommentDto>> getComment(Long articleId);

  EntityModel<CommentDto> getByDetail(Long commentId, User authenticatedUser);

  void delete(Long commentId, User authenticatedUser);

  EntityModel<CommentDto> changeContent(Long commentId, String content, User authenticatedUser);

  CollectionModel<EntityModel<CommentDto>> getComment(Long articleId, String username);
}
