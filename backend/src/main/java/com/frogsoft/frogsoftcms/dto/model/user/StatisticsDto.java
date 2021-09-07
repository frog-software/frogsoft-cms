package com.frogsoft.frogsoftcms.dto.model.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StatisticsDto {

  private Integer publishArticlesNum;
  private Integer viewsNum;
  private Integer likesNum;
  private Integer favoritesNum;
}
