package com.frogsoft.frogsoftcms.dto.model.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FrontConfigDto {
  private String favicon;
  private String title;
  private String logo;
  private HeaderDto headerDto;
  private FooterDto footerDto;
}
