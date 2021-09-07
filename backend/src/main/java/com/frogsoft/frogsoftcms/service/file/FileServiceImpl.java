package com.frogsoft.frogsoftcms.service.file;

import com.frogsoft.frogsoftcms.dto.assembler.file.FileModelAssemble;
import com.frogsoft.frogsoftcms.dto.mapper.file.FileMapper;
import com.frogsoft.frogsoftcms.dto.model.file.FileDto;
import com.frogsoft.frogsoftcms.exception.article.ArticleNotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.badrequest.BadRequestException;
import com.frogsoft.frogsoftcms.exception.basic.forbidden.ForbiddenException;
import com.frogsoft.frogsoftcms.exception.basic.unauthorized.UnauthorizedException;
import com.frogsoft.frogsoftcms.model.file.Material;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.file.FileRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService{

  @Value("${file.upload.dir}")
  private String FILE_UPLOAD_DIR;

  private final FileRepository fileRepository;
  private final FileModelAssemble fileModelAssemble;
  private final FileMapper fileMapper;

  @Override
  public EntityModel<FileDto> putFile(User authenticatedUser, MultipartFile file){
    String path = authenticatedUser.getUsername() + "/";
    String fileName = file.getOriginalFilename();
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    UUID uuid = UUID.randomUUID();
    String newFileName = uuid + suffixName;

    System.out.println(FILE_UPLOAD_DIR + path);
    File fileDirectory = new File(FILE_UPLOAD_DIR + path);
    File destFile = new File(FILE_UPLOAD_DIR + path + newFileName);
    if (!fileDirectory.exists()) {
      if (!fileDirectory.mkdir()) {
        throw new ForbiddenException("创建文件夹失败");
      }
    }
    try {
      file.transferTo(destFile.getAbsoluteFile());
    } catch (IOException e) {
      e.printStackTrace();
      throw new BadRequestException("上传失败，请重新尝试");
    }
    Material material = new Material();
    material.setShortName(fileName);
    material.setFullName(newFileName);
    material.setUrl(destFile.getAbsolutePath());
    material.setSize(file.getSize());
    material.setType(file.getContentType());
    material.setPublishDate(LocalDateTime.now());
    material.setUser(authenticatedUser);
    return fileModelAssemble.toModel(fileMapper.toFileDto(fileRepository.save(material)));
  }

  @Override
  public void deleteFile(User authenticatedUser, Long id){
    Material material = fileRepository.findById(id).orElseThrow(
        () -> new  BadRequestException("文件不存在"));
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")) {
      if (!material.getUser().equals(authenticatedUser)) {
        throw new UnauthorizedException("无权限");
      }
    }
    File file = new File(material.getUrl());
    if(file.exists()) {
      file.delete();
    }
    else {
      throw new BadRequestException("删除失败");
    }
    fileRepository.delete(material);
  }
}
