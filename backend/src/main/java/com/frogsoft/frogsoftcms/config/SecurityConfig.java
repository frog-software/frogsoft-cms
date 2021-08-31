package com.frogsoft.frogsoftcms.config;

import com.frogsoft.frogsoftcms.exception.user.UserNotFoundException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import com.frogsoft.frogsoftcms.security.RestAuthenticationEntryPoint;
import com.frogsoft.frogsoftcms.security.jwt.JwtTokenAuthenticationFilter;
import com.frogsoft.frogsoftcms.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
@AllArgsConstructor
public class SecurityConfig {

  @Bean
  SecurityFilterChain springWebFilterChain(
      HttpSecurity http,
      JwtTokenProvider tokenProvider,
      RestAuthenticationEntryPoint restAuthenticationEntryPoint
  ) throws Exception {

    return http.httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(c ->
            c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests(c ->
            c.antMatchers("/v1/auth/login").permitAll()
                .anyRequest().authenticated()
        )
        .addFilterBefore(
            new JwtTokenAuthenticationFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  UserDetailsService customUserDetailsService(UserRepository userRepository) {
    return username -> {
      User user = userRepository.findByUsername(username);

      if (user == null) {
        throw new UserNotFoundException(username);
      }

      return user;
    };
  }

  @Bean
  AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService,
      PasswordEncoder encoder) {
    return authentication -> {
      String username = authentication.getPrincipal() + "";
      String password = authentication.getCredentials() + "";
      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (!encoder.matches(password, user.getPassword())) {
        throw new BadCredentialsException("Bad credentials");
      }
      if (!user.isEnabled()) {
        throw new DisabledException("User account is not active");
      }
      return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
