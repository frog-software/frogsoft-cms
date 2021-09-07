package com.frogsoft.frogsoftcms.config;

import java.time.LocalDateTime;

public class VerificationCode {

  private String email;
  private String code;
  private LocalDateTime generationTime;

  public VerificationCode(String email, String code) {
    this.email = email;
    this.code = code;
    this.generationTime = LocalDateTime.now();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(Long userId) {
    this.email = email;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getGenerationTime() {
    return generationTime;
  }

  public void setGenerationTime(LocalDateTime generationTime) {
    this.generationTime = generationTime;
  }
}
