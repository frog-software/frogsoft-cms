package com.frogsoft.frogsoftcms.model.other;


import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class Other {

  @Id
  private String key;
  private String value;
}
