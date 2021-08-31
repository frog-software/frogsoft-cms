package com.frogsoft.frogsoftcms.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.frogsoft.frogsoftcms.exception.basic.ExceptionResponseBody;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    } else if (token != null) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.findAndRegisterModules();
      objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssSSS"));

      ExceptionResponseBody exceptionResponseBody = new ExceptionResponseBody(
          HttpStatus.UNAUTHORIZED.value(), "Token 无效", "Unauthorized");
      res.setCharacterEncoding("utf-8");

      res.getWriter().write(objectMapper.writeValueAsString(exceptionResponseBody));
      HttpServletResponse httpServletResponse = (HttpServletResponse) res;
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
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
