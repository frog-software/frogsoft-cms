package com.frogsoft.frogsoftcms.dto.assembler.config;

import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ConfigModelAssembler implements
    RepresentationModelAssembler<ConfigDto, EntityModel<ConfigDto>> {
  @Override
  public EntityModel<ConfigDto> toModel(ConfigDto configDto){
    return EntityModel.of(
        configDto
    );
  }
}
