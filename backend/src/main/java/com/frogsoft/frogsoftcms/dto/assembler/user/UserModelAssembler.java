package com.frogsoft.frogsoftcms.dto.assembler.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.frogsoft.frogsoftcms.controller.v1.api.UserController;
import com.frogsoft.frogsoftcms.dto.model.user.UserDetailDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements
    RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

  @Override
  public EntityModel<UserDto> toModel(UserDto userDto) {
    return EntityModel.of(
        userDto,
        linkTo(methodOn(UserController.class).getOneUser(userDto.getUsername(), null))
            .withSelfRel(),
        linkTo(methodOn(UserController.class).getAllUsers(0, 10)).withRel("allUsers")
    );
  }

  public EntityModel<UserDetailDto> toDetailModel(UserDetailDto userDetailDto) {
    return EntityModel.of(
        userDetailDto,
        linkTo(methodOn(UserController.class).getOneUser(userDetailDto.getUsername(), null))
            .withSelfRel(),
        linkTo(methodOn(UserController.class).getAllUsers(0, 10)).withRel("allUsers")
    );
  }
}
