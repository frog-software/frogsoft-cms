package com.frogsoft.frogsoftcms.dto.mapper.file;

import com.frogsoft.frogsoftcms.dto.mapper.user.UserMapper;
import com.frogsoft.frogsoftcms.dto.model.file.FileDto;
import com.frogsoft.frogsoftcms.model.file.Material;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FileMapper {

  private final UserMapper userMapper;

  public FileDto toFileDto(Material material) {
    return new FileDto().setFullName(material.getFullName())
        .setId(material.getId())
        .setPublishDate(material.getPublishDate())
        .setShortName(material.getShortName())
        .setSize(material.getSize())
        .setUrl(material.getUrl())
        .setUser(userMapper.toUserDto(material.getUser()))
        .setType(material.getType());
  }
}
