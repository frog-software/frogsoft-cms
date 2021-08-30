package com.frogsoft.frogsoftcms.security;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

  @Value("${jwt.secret}")
  public static String SECRET_KEY;
  public static String TOKEN_PREFIX = "Bearer ";
  public static String HEADER_STRING = "Authorization";
  @Value("${jwt.expiration-delay}")
  public static String EXPIRATION_TIME;
}
