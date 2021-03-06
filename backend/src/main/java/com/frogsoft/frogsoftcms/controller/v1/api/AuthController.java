package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.controller.v1.request.auth.AuthRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.auth.ResetRequest;
import com.frogsoft.frogsoftcms.exception.basic.unauthorized.UnauthorizedException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.security.jwt.JwtTokenProvider;
import com.frogsoft.frogsoftcms.service.auth.AuthService;
import com.frogsoft.frogsoftcms.service.mail.MailService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthService authService;
  private final MailService mailService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

    try {
      String username = authRequest.getUsername();

      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));

      String token = jwtTokenProvider.createToken(authentication);
      Map<Object, Object> model = new HashMap<>();

      model.put("username", username);
      model.put("token", token);

      return ResponseEntity.status(201).body(model);

    } catch (AuthenticationException e) {
      throw new UnauthorizedException("????????????????????????");
    }
  }

  @PostMapping("/forget")
  public ResponseEntity<?> getCode(@RequestParam String username) {
    User user = authService.findByUsername(username);
    mailService.sendCode(user.getEmail());
    Map<Object, Object> model = new HashMap<>();
    model.put("email", user.getEmail());
    model.put("username", user.getUsername());
    return ResponseEntity.status(201).body(model);
  }

  @PutMapping("/forget")
  public ResponseEntity<?> verifyCode(@RequestBody ResetRequest resetRequest) {
    String username = resetRequest.getUsername();
    String code = resetRequest.getVaryficationcode();
    String newPassword = resetRequest.getNewpassword();
    return ResponseEntity.status(201).body(authService.resetPassword(username, code, newPassword));
  }
}
