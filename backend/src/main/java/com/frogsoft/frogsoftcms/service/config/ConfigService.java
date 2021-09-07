package com.frogsoft.frogsoftcms.service.config;

import com.frogsoft.frogsoftcms.controller.v1.request.config.ConfigRequest;
import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import org.springframework.hateoas.EntityModel;

public interface ConfigService {
  EntityModel<ConfigDto> getConfig();

  EntityModel<ConfigDto> putConfig(ConfigRequest configRequest);
}
