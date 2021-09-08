package com.frogsoft.frogsoftcms.init;

import com.frogsoft.frogsoftcms.controller.v1.request.config.ConfigRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.config.EmailRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.config.FooterRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.config.HeaderRequest;
import com.frogsoft.frogsoftcms.model.config.Config;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.config.ConfigRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import com.frogsoft.frogsoftcms.service.config.ConfigService;
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
      PasswordEncoder passwordEncoder,
      ConfigService configService
  ) {

    return args -> {

      // store a default admin user in the user repository
      User user = userRepository.findByUsername("admin");

      if (user == null) {
        userRepository.save(new User()
            .setEmail("admin@frogsoft.com")
            .setPassword(passwordEncoder.encode("123456"))
            .setUsername("admin")
            .setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
            .setAvatar("")
        );
      }

      // store default keys and values in the config repository
      Config dailyPickup = configRepository.findByConfigKey("DailyPickup");
      if (dailyPickup == null) {
        configRepository.save(new Config()
            .setConfigKey("DailyPickup")
            .setConfigValue("0")

        );
      }
      Config announcementsId = configRepository.findByConfigKey("AnnouncementsId");
      if (announcementsId == null) {
        configRepository.save(new Config()
            .setConfigKey("AnnouncementsId")
            .setConfigValue("0,")
        );
      }

      Config configEmpty = configRepository.findByConfigKey("favicon");
      if (configEmpty == null) {
        configService.putConfig((new ConfigRequest())
            .setEmail(
                (new EmailRequest())
                    .setAccount("example_account")
                    .setTitle("验证码")
                    .setBody("这是你的验证码，请勿外传：[[${verifyCode}]]")
                    .setHost("example.com")
                    .setPassword("password")
                    .setPort("465")
            )
            .setFavicon("")
            .setFooter(
                (new FooterRequest())
                    .setLogo("")
            )
            .setHeader(
                (new HeaderRequest())
                    .setLogo("")
            )
            .setTitle("Frogsoft CMS")
            .setLogo("")
        );
      }

    };
  }
}
