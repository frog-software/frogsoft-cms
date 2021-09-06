package com.frogsoft.frogsoftcms.init;

import com.frogsoft.frogsoftcms.model.config.Config;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.config.ConfigRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Initialize {

  @Bean
  CommandLineRunner init(
      UserRepository userRepository,
      ConfigRepository configRepository,
      PasswordEncoder passwordEncoder
  ) {

    return args -> {

      // store a default admin user in the user repository
      User user = userRepository.findByUsername("admin");

      if (user == null) {
        userRepository.save(new User()
            .setEmail("admin@frogsoft.com")
            .setPassword(passwordEncoder.encode("123456"))
            .setUsername("admin")
            .setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
      }

      // store default keys and values in the config repository
      Config dailyPickup = configRepository.findByConfigKey("DailyPickup");
      if(dailyPickup == null){
        configRepository.save(new Config()
            .setConfigKey("DailyPickup")
            .setConfigValue("0")

        );
      }
      Config announcementsId = configRepository.findByConfigKey("AnnouncementsId");
      if(announcementsId == null){
        configRepository.save(new Config()
            .setConfigKey("AnnouncementsId")
            .setConfigValue("0,")
        );
      }

    };
  }
}
