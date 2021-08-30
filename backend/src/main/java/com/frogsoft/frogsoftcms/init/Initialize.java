package com.frogsoft.frogsoftcms.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initialize {

  @Bean
  CommandLineRunner init() {
    return args -> {
    };
  }
}
