package com.frogsoft.frogsoftcms.dto.history;

import com.frogsoft.frogsoftcms.dto.mapper.history.HistoryMapper;
import com.frogsoft.frogsoftcms.dto.model.history.HistoryDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class HistoryModelAssembler implements
    RepresentationModelAssembler<HistoryDto, EntityModel<HistoryDto>> {

  @Override
  public EntityModel<HistoryDto> toModel(HistoryDto historyDto){
    return EntityModel.of(
        historyDto
    );
  }
}
