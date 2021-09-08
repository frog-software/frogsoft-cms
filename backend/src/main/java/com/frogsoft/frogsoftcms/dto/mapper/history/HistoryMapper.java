package com.frogsoft.frogsoftcms.dto.mapper.history;

import com.frogsoft.frogsoftcms.dto.model.history.HistoryDto;
import com.frogsoft.frogsoftcms.model.history.History;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HistoryMapper {

  public HistoryDto toHistoryDto(History history) {
    return new HistoryDto()
        .setId(history.getId())
        .setTime(history.getTime())
        .setUserId(history.getUser().getId())
        .setArticleId(history.getArticle().getId());
  }

  public List<HistoryDto> toHistoryDto(List<History> histories) {
    List<HistoryDto> historyDtoList = new LinkedList<>();
    for (History history : histories) {
      historyDtoList.add(new HistoryDto()
          .setId(history.getId())
          .setTime(history.getTime())
          .setUserId(history.getUser().getId())
          .setArticleId(history.getArticle().getId())
      );
    }
    return historyDtoList;
  }
}
