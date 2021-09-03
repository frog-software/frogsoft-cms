package com.frogsoft.frogsoftcms.dto.assembler.comment;

import com.frogsoft.frogsoftcms.dto.model.comment.CommentDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CommentModelAssembler implements
    RepresentationModelAssembler<CommentDto, EntityModel<CommentDto>> {

  @Override
  public EntityModel<CommentDto> toModel(CommentDto commentDto) {
    return EntityModel.of(
        commentDto
    );
  }
}
