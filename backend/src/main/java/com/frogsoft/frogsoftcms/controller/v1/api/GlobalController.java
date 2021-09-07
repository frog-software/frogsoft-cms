package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/global")
public class GlobalController {

  private final MailService mailService;

  @PostMapping("/email")
  public ResponseEntity<?> sendCode(@AuthenticationPrincipal User authenticatedUser) {
    String username = authenticatedUser.getUsername();
    return ResponseEntity.ok().body(mailService.sendCode(username));
  }
}
