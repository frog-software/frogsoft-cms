package com.frogsoft.frogsoftcms.service.comment;

import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.user.User;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface CommentService {

  EntityModel<CommentDto> saveComment(Long articleId, CommentRequest commentRequest,
      User authenticatedUser);

  CollectionModel<EntityModel<CommentDto>> getComment(Long articleId, User authenticatedUser);

  EntityModel<CommentDto> get(Long commentId, User authenticatedUser);

  void delete(Long commentId, User authenticatedUser);

  EntityModel<CommentDto> changeContent(Long commentId, String content,User authenticatedUser);
}
