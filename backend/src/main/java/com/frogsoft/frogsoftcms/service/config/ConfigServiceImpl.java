package com.frogsoft.frogsoftcms.service.config;

import com.frogsoft.frogsoftcms.controller.v1.request.config.ConfigRequest;
import com.frogsoft.frogsoftcms.dto.assembler.config.ConfigModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.config.ConfigMapper;
import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import com.frogsoft.frogsoftcms.model.config.Config;
import com.frogsoft.frogsoftcms.repository.config.ConfigRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConfigServiceImpl implements ConfigService{

  private final ConfigRepository configRepository;
  private final ConfigModelAssembler configModelAssembler;
  private final ConfigMapper configMapper;

  @Override
  public EntityModel<ConfigDto> getConfig(){
    List<Config> configList = configRepository.findAll();
    return configModelAssembler.toModel(configMapper.toConfigDto(configList));
  }

  @Override
  public EntityModel<ConfigDto> putConfig(ConfigRequest configRequest){
    List<Config> configList = new ArrayList<>();
    configList.add(new Config()
        .setConfigKey("favicon")
        .setConfigValue(configRequest.getFavicon()));
    configList.add(new Config()
        .setConfigKey("title")
        .setConfigValue(configRequest.getTitle()));
    configList.add((new Config()
        .setConfigKey("logo")
        .setConfigValue(configRequest.getLogo())));
    configList.add(new Config()
        .setConfigKey("email_password")
        .setConfigValue(configRequest.getEmail().getPassword()));
    configList.add(new Config()
        .setConfigKey("email_account")
        .setConfigValue(configRequest.getEmail().getAccount()));
    configList.add(new Config()
        .setConfigKey("email_body")
        .setConfigValue(configRequest.getEmail().getBody()));
    configList.add(new Config()
        .setConfigKey("email_title")
        .setConfigValue(configRequest.getEmail().getTitle()));
    configList.add(new Config()
        .setConfigKey("header_logo")
        .setConfigValue(configRequest.getHeader().getLogo()));
    configList.add(new Config()
        .setConfigKey("footer_logo")
        .setConfigValue(configRequest.getFooter().getLogo()));
    List<Config> configs = configRepository.saveAll(configList);
    return configModelAssembler.toModel(configMapper.toConfigDto(configs));
  }
}
