package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.controller.v1.request.home.AnnouncementsSetRequest;
import com.frogsoft.frogsoftcms.exception.basic.forbidden.ForbiddenException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.home.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PutMapping("/daily")
  public ResponseEntity<?> putDailyArticle(
      @RequestParam Integer articleId,
      @AuthenticationPrincipal User authenticatedUser) {
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")) {
      throw new ForbiddenException("需要管理员权限");
    }
    return ResponseEntity.status(201).body(homeService.changeDailyArticle(articleId,authenticatedUser));
  }
  
  @GetMapping("/hot-articles")
  public ResponseEntity<?> getRankList(){
    return ResponseEntity.status(200).body(homeService.getRankList());
  }

  @GetMapping("/announcements")
  public ResponseEntity<?> getAnnouncements(){
    return ResponseEntity.status(200).body(homeService.getAnnouncements());
  }

  @PutMapping("/announcements")
  public ResponseEntity<?> putAnnouncements(
      @RequestBody AnnouncementsSetRequest announcementsSetRequest ,
      @AuthenticationPrincipal User authenticatedUser) {
    if (!authenticatedUser.getRoles().contains("ROLE_ADMIN")) {
      throw new ForbiddenException("需要管理员权限");
    }
    return ResponseEntity.status(201).body(homeService.changeAnnouncements(
        announcementsSetRequest, authenticatedUser));
  }
  
}
