package com.frogsoft.frogsoftcms.service.file;

import com.frogsoft.frogsoftcms.dto.model.file.FileDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  EntityModel<FileDto> putFile(User authenticatedUser, MultipartFile file);

  void deleteFile(User authenticatedUser, Long id);
}
