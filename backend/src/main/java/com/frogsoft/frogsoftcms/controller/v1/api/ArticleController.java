package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.article.ArticleService;
import com.frogsoft.frogsoftcms.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/articles")
public class ArticleController {

  private final ArticleService articleService;
  private final CommentService commentService;

  @PostMapping("")
  public ResponseEntity<?> createArticles(@RequestBody ArticleRequest articleRequest,
      @AuthenticationPrincipal User authenticatedUser
  ) {
    return ResponseEntity.status(201)
        .body(articleService.saveArticles(articleRequest, authenticatedUser));
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<?> createComments(@PathVariable(name = "id") Long id, @RequestBody
      CommentRequest commentRequest, @AuthenticationPrincipal User authenticatedUser) {
    return ResponseEntity.status(201)
        .body(commentService.saveComment(id, commentRequest, authenticatedUser));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<?> getComments(@PathVariable(name = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    return ResponseEntity.status(201).body(commentService.getComment(id, authenticatedUser));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOneArticle(@PathVariable(value = "id") Long id) {
    return ResponseEntity.status(201).body(articleService.getOneArticle(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editArticle(@PathVariable(value = "id") Long id,
      @RequestBody ArticleRequest articleRequest,
      @AuthenticationPrincipal User authenticatedUser) {
    Long userId = authenticatedUser.getId();
    return ResponseEntity.status(201).body(articleService.editArticle(id, userId, articleRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteArticle(@PathVariable(value = "id") Long id,
      @AuthenticationPrincipal User authenticateUser) {
    Long userId = authenticateUser.getId();
    articleService.deleteArticle(id, userId);
    return ResponseEntity.noContent().build();
  }
}
