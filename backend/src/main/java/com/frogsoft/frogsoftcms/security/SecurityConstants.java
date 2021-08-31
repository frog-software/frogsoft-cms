package com.frogsoft.frogsoftcms.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class SecurityConstants {

  @Value("${jwt.secret}")
  public String SECRET_KEY;
  public String HEADER_STRING = "Authorization";
  @Value("${jwt.expiration-delay}")
  public String EXPIRATION_TIME;
}
