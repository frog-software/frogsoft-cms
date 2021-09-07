package com.frogsoft.frogsoftcms.repository.config;

import com.frogsoft.frogsoftcms.model.config.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, String> {
  Config findByConfigKey(String configKey);
}
