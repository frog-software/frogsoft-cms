package com.frogsoft.frogsoftcms;


import com.frogsoft.frogsoftcms.config.VerificationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableCaching
public class FrogsoftCmsBackendApplication {

  public static VerificationConfig verificationCodeStorage = new VerificationConfig();

  public static void main(String[] args) {
    SpringApplication.run(FrogsoftCmsBackendApplication.class, args);
  }
}
