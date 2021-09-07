package com.frogsoft.frogsoftcms.dto.mapper.config;


import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import com.frogsoft.frogsoftcms.dto.model.config.EmailDto;
import com.frogsoft.frogsoftcms.dto.model.config.FooterDto;
import com.frogsoft.frogsoftcms.dto.model.config.HeaderDto;
import com.frogsoft.frogsoftcms.model.config.Config;
import com.frogsoft.frogsoftcms.repository.config.ConfigRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConfigMapper {

  private final ConfigRepository configRepository;

  public ConfigDto toConfigDto(List<Config> configList) {
    ConfigDto configDto = new ConfigDto();
    EmailDto emailDto = new EmailDto();
    HeaderDto headerDto = new HeaderDto();
    FooterDto footerDto = new FooterDto();
    footerDto.setLogo(configRepository.findByConfigKey("footer_logo").getConfigValue());
    headerDto.setLogo(configRepository.findByConfigKey("header_logo").getConfigValue());
    emailDto.setPort(configRepository.findByConfigKey("email_port").getConfigValue());
    emailDto.setHost(configRepository.findByConfigKey("email_host").getConfigValue());
    emailDto.setTitle(configRepository.findByConfigKey("email_title").getConfigValue());
    emailDto.setBody(configRepository.findByConfigKey("email_body").getConfigValue());
    emailDto.setAccount(configRepository.findByConfigKey("email_account").getConfigValue());
    emailDto.setPassword(configRepository.findByConfigKey("email_password").getConfigValue());
    configDto.setLogo(configRepository.findByConfigKey("logo").getConfigValue());
    configDto.setTitle(configRepository.findByConfigKey("title").getConfigValue());
    configDto.setFavicon(configRepository.findByConfigKey("favicon").getConfigValue());
    configDto.setEmailDto(emailDto);
    configDto.setHeaderDto(headerDto);
    configDto.setFooterDto(footerDto);
    return configDto;
  }
}