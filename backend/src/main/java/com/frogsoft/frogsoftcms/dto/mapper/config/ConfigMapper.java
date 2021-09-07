package com.frogsoft.frogsoftcms.dto.mapper.config;


import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import com.frogsoft.frogsoftcms.dto.model.config.EmailDto;
import com.frogsoft.frogsoftcms.dto.model.config.FooterDto;
import com.frogsoft.frogsoftcms.dto.model.config.HeaderDto;
import com.frogsoft.frogsoftcms.model.config.Config;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConfigMapper {

  public ConfigDto toConfigDto(List<Config> configList) {
    ConfigDto configDto = new ConfigDto();
    EmailDto emailDto = new EmailDto();
    HeaderDto headerDto = new HeaderDto();
    FooterDto footerDto = new FooterDto();
    footerDto.setLogo(configList.get(8).getConfigValue());
    headerDto.setLogo(configList.get(7).getConfigValue());
    emailDto.setTitle(configList.get(6).getConfigValue());
    emailDto.setBody(configList.get(5).getConfigValue());
    emailDto.setAccount(configList.get(4).getConfigValue());
    emailDto.setPassword(configList.get(3).getConfigValue());
    configDto.setLogo(configList.get(2).getConfigValue());
    configDto.setTitle(configList.get(1).getConfigValue());
    configDto.setFavicon(configList.get(0).getConfigValue());
    configDto.setEmailDto(emailDto);
    configDto.setHeaderDto(headerDto);
    configDto.setFooterDto(footerDto);
    return configDto;
  }
}
