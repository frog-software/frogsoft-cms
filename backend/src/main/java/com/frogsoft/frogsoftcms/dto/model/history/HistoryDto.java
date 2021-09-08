package com.frogsoft.frogsoftcms.dto.model.history;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HistoryDto {
  private Long id;
  private LocalDateTime time;
  private Long articleId;
  private Long userId;
}
