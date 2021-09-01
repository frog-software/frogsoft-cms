package com.frogsoft.frogsoftcms.security.jwt;

import static java.util.stream.Collectors.joining;

import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import com.frogsoft.frogsoftcms.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  private static final String AUTHORITIES_KEY = "roles";
  private final SecurityConstants securityConstants;
  private final UserRepository userRepository;
  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    var secret = Base64.getEncoder().encodeToString(securityConstants.SECRET_KEY.getBytes());
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String createToken(Authentication authentication) {
    String username = authentication.getName();

    User user = userRepository.findByUsername(username);

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    Claims claims = Jwts.claims().setSubject(username).setId(user.getId().toString());

    if (!authorities.isEmpty()) {
      claims.put(AUTHORITIES_KEY,
          authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
    }

    Date now = new Date();
    int expireDelay = Integer.parseInt(securityConstants.EXPIRATION_TIME);
    Date validity = new Date(now.getTime() + expireDelay * 1000L);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(this.secretKey, SignatureAlgorithm.HS256)
        .compact();

  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(this.secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();

    Object authoritiesClaim = claims.get(AUTHORITIES_KEY);

    Collection<? extends GrantedAuthority> authorities =
        authoritiesClaim == null
            ? AuthorityUtils.NO_AUTHORITIES
            : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

    try {
      User principal = new User()
          .setId(Long.parseLong(claims.getId()))
          .setUsername(claims.getSubject())
          .setRoles(authorities
              .stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList()));

      return new UsernamePasswordAuthenticationToken(principal, token, authorities);

    } catch (NumberFormatException e) {
      return null;
    }
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
