package com.frogsoft.frogsoftcms.security.jwt;


import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;


@AllArgsConstructor
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {

    String token = resolveToken((HttpServletRequest) req);
    boolean isTokenValid = jwtTokenProvider.validateToken(token);

    if (token != null && isTokenValid) {
      Authentication auth = jwtTokenProvider.getAuthentication(token);

      if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
        SecurityContextHolder.getContext().setAuthentication(auth);
      }

    }

    filterChain.doFilter(req, res);
  }

  private String resolveToken(HttpServletRequest request) {

    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }
}
