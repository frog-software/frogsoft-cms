package com.frogsoft.frogsoftcms.service.comment;

import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.EntityModel;

public interface CommentService {

  EntityModel<CommentDto> saveComment(Long articleId, CommentRequest commentRequest,
      User authenticatedUser);
}
