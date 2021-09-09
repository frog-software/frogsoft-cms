package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.controller.v1.request.config.ConfigRequest;
import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import com.frogsoft.frogsoftcms.dto.model.file.FileDto;
import com.frogsoft.frogsoftcms.exception.basic.badrequest.BadRequestException;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.unauthorized.UnauthorizedException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.config.ConfigService;
import com.frogsoft.frogsoftcms.service.file.FileService;
import com.frogsoft.frogsoftcms.service.mail.MailService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/global")
public class GlobalController {

  private final MailService mailService;
  private final ConfigService configService;
  private final FileService fileService;

  @Value("${file.upload.dir}")
  private String FILE_UPLOAD_DIR;

  @PostMapping("/email")
  public ResponseEntity<?> sendCode(@RequestParam String email) {
    mailService.sendCode(email);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/config/frontend-user")
  public ResponseEntity<?> getFrontendConfig() {
    return ResponseEntity.ok().body(configService.getFrontConfig());
  }

  @GetMapping("/config")
  public ResponseEntity<EntityModel<ConfigDto>> getConfig(
      @AuthenticationPrincipal User authenticatedUser) {
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")) {
      throw new UnauthorizedException("权限不足");
    }
    return ResponseEntity.ok().body(configService.getConfig());
  }

  @PutMapping("/config")
  public ResponseEntity<EntityModel<ConfigDto>> putConfig(
      @AuthenticationPrincipal User authenticatedUser,
      @RequestBody ConfigRequest configRequest) {
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")) {
      throw new UnauthorizedException("权限不足");
    }
    return ResponseEntity.ok().body(configService.putConfig(configRequest));
  }

  @PostMapping("/files")
  public ResponseEntity<EntityModel<FileDto>> putFile(
      @AuthenticationPrincipal User authenticatedUser,
      @RequestParam("file") MultipartFile file,
      HttpServletRequest request
  ) {
    return ResponseEntity.ok().body(fileService.putFile(authenticatedUser, file, request));
  }

  @DeleteMapping("/files")
  public ResponseEntity<?> deleteFile(
      @AuthenticationPrincipal User authenticatedUser,
      @RequestBody Long id) {
    fileService.deleteFile(authenticatedUser, id);
    return ResponseEntity.ok(201);
  }

  @GetMapping("/files/{filename}")
  public void serveStatic(
      @PathVariable String filename,
      HttpServletResponse response
  ) throws IOException {

    String path = FILE_UPLOAD_DIR + "/" + filename;

    String cwd = Paths.get(".").toAbsolutePath().normalize().toString();
    File file = new File(cwd + "/" + path);

    if (!file.exists()) {
      throw new NotFoundException("请求的文件 " + filename + " 不存在");
    }

    try {
      FileInputStream inStream = new FileInputStream(file);
      OutputStream outStream = response.getOutputStream();
      String mimetype = Files.probeContentType(file.toPath());
      response.setContentType(mimetype);

      byte[] buffer = new byte[4096];
      int bytesRead = -1;

      while ((bytesRead = inStream.read(buffer)) != -1) {
        outStream.write(buffer, 0, bytesRead);
      }

      inStream.close();
      outStream.close();
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage());
    }

  }
}
