package com.frogsoft.frogsoftcms.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class VerificationConfig {

  // 验证码重发时间间隔
  private final int RESEND_INTERVAL = 0;
  // 验证码过期时间
  private final int EXPIRATION_TIME = 120;

  private final HashMap<Long, VerificationCode> storage = new HashMap<>();

  // 生成验证码，生成失败返回 null
  public VerificationCode generateCode(Long userId) {

    VerificationCode previousCode = storage.get(userId);

    if (previousCode != null && previousCode.getGenerationTime()
        .isAfter(LocalDateTime.now().minusSeconds(RESEND_INTERVAL))) {
      return null;
    }

    String code = Integer.toString((int) (Math.random() * 9000 + 1000));
    VerificationCode verificationCode = new VerificationCode(userId, code);

    storage.put(userId, verificationCode);

    return verificationCode;
  }

  // 验证验证码，验证失败返回 null
  public VerificationCode verifyCode(Long userId, String code) {
    deleteExpiredCode();

    VerificationCode storedCode = storage.get(userId);

    if (storedCode == null || !storedCode.getCode().equals(code)) {
      return null;
    }

    return storedCode;
  }

  private void deleteExpiredCode() {
    ArrayList<Long> codeToBeDeletedList = new ArrayList<>();

    for (var entry : storage.entrySet()) {
      if (entry.getValue().getGenerationTime().isBefore(
          LocalDateTime.now().minusSeconds(EXPIRATION_TIME))
      ) {
        codeToBeDeletedList.add(entry.getKey());
      }
    }

    for (Long codeToBeDeleted : codeToBeDeletedList) {
      storage.remove(codeToBeDeleted);
    }


  }

}
