package com.frogsoft.frogsoftcms.dto.assembler.file;
import com.frogsoft.frogsoftcms.dto.model.file.FileDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FileModelAssemble implements
    RepresentationModelAssembler<FileDto,EntityModel<FileDto>> {
  @Override
  public EntityModel<FileDto> toModel(FileDto fileDto) {
    return EntityModel.of(
        fileDto
    );
  }
}
