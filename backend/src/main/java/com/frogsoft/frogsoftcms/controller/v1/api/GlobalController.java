package com.frogsoft.frogsoftcms.controller.v1.api;


import com.frogsoft.frogsoftcms.controller.v1.request.config.ConfigRequest;
import com.frogsoft.frogsoftcms.dto.model.config.ConfigDto;
import com.frogsoft.frogsoftcms.exception.basic.unauthorized.UnauthorizedException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.config.ConfigService;

import com.frogsoft.frogsoftcms.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/global")
public class GlobalController {

  private final MailService mailService;
  private final ConfigService configService;

  @PostMapping("/email")
  public ResponseEntity<?> sendCode(@RequestParam String email) {
    mailService.sendCode(email);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/config")
  public ResponseEntity<EntityModel<ConfigDto>> getConfig(
      @AuthenticationPrincipal User authenticatedUser){
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")){
      throw new  UnauthorizedException("权限不足");
    }
    return ResponseEntity.ok().body(configService.getConfig());
  }

  @PutMapping("/config")
  public ResponseEntity<EntityModel<ConfigDto>> putConfig(
      @AuthenticationPrincipal User authenticatedUser,
      @RequestBody ConfigRequest configRequest){
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")){
      throw new  UnauthorizedException("权限不足");
    }
    return ResponseEntity.ok().body(configService.putConfig(configRequest));
  }
}
