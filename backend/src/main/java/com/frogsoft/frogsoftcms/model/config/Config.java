package com.frogsoft.frogsoftcms.model.config;


import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class Config {

  @Id
  private String configKey;
  private String configValue;
}
