package com.frogsoft.frogsoftcms.dto.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.frogsoft.frogsoftcms.controller.v1.api.UserController;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

  @Override
  public EntityModel<User> toModel(User user) {
    return EntityModel.of(
        user,
        linkTo(methodOn(UserController.class).getOneUser(user.getUsername())).withSelfRel());
  }
}
