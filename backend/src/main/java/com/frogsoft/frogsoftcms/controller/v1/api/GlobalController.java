package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/global")
public class GlobalController {

  private final MailService mailService;

  @PostMapping("/email")
  public ResponseEntity<?> sendCode(@RequestParam String email) {
    mailService.sendCode(email);
    return ResponseEntity.noContent().build();
  }
}
