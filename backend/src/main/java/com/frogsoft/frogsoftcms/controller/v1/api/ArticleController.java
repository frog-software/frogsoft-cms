package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/articles")
public class ArticleController {

  private final ArticleService articleService;

  @PostMapping("")
  public ResponseEntity<?> createarticles(@RequestBody ArticleRequest articleRequest,
      @AuthenticationPrincipal User authenticatedUser
  ) {
    return ResponseEntity.status(201)
        .body(articleService.saveArticles(articleRequest, authenticatedUser));
  }
}
