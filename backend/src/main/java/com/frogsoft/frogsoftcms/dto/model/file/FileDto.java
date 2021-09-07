package com.frogsoft.frogsoftcms.dto.model.file;

import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileDto {
  private Long id;
  private UserDto user;
  private String shortName;
  private String fullName;
  private String type;
  private Long size;
  private String url;
  private LocalDateTime publishDate;
}
