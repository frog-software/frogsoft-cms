package com.frogsoft.frogsoftcms.config;

import java.time.LocalDateTime;

public class VerificationCode {

  private Long userId;
  private String code;
  private LocalDateTime generationTime;

  public VerificationCode(Long userId, String code) {
    this.userId = userId;
    this.code = code;
    this.generationTime = LocalDateTime.now();
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
