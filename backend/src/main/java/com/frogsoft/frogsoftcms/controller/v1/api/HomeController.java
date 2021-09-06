package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.home.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/home")
public class HomeController {

  private final HomeService homeService;

  @GetMapping("/recommendations")
  public ResponseEntity<?> getRecommendations(@AuthenticationPrincipal User authenticatedUser) {
    return ResponseEntity.status(200).body(homeService.getRecommendations(authenticatedUser));
  }

  @GetMapping("/daily")
  public ResponseEntity<?> getDailyArticle() {
    return ResponseEntity.status(200).body(homeService.getDailyArticle());
  }
  
  @GetMapping("/hot-articles")
  public ResponseEntity<?> getRankList(){
    return ResponseEntity.status(200).body(homeService.getRankList());
  }
  
}
