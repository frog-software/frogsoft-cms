package com.frogsoft.frogsoftcms.model.user;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@NotNull
@Setter
@Getter
@Embeddable
public class Username {

  private String username;
}
