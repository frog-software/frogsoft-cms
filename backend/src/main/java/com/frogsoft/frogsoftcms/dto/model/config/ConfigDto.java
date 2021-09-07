package com.frogsoft.frogsoftcms.dto.model.config;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfigDto {

  private String favicon;
  private String title;
  private String logo;
  private EmailDto emailDto;
  private HeaderDto headerDto;
  private FooterDto footerDto;
}
