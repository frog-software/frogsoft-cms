package com.frogsoft.frogsoftcms.dto.assembler.config;

import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import com.frogsoft.frogsoftcms.dto.model.config.FrontConfigDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ConfigModelAssembler implements
    RepresentationModelAssembler<ConfigDto, EntityModel<ConfigDto>> {

  @Override
  public EntityModel<ConfigDto> toModel(ConfigDto configDto) {
    return EntityModel.of(
        configDto
    );
  }

  public EntityModel<FrontConfigDto> toModel(FrontConfigDto frontConfigDto) {
    return EntityModel.of(
        frontConfigDto
    );
  }
}
