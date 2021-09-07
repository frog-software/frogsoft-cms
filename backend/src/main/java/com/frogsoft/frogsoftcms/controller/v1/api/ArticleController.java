package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.controller.v1.request.comment.CommentRequest;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.article.ArticleService;
import com.frogsoft.frogsoftcms.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/articles")
public class ArticleController {

  private final ArticleService articleService;
  private final CommentService commentService;

  @GetMapping("")
  public ResponseEntity<?> search(
      @RequestParam(defaultValue = "") String search,
      @RequestParam(defaultValue = "publishDate") String sortBy,
      @RequestParam(defaultValue = "DESC") String order,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    if (search.equals("")) {
      return ResponseEntity.ok()
          .body(articleService.findAll(sortBy, order, PageRequest.of(page, size)));
    } else {
      return ResponseEntity.ok()
          .body(articleService.findBySearch(search, sortBy, order, PageRequest.of(page, size)));
    }
  }

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
  public ResponseEntity<?> getComments(@PathVariable(name = "id") Long id) {
    return ResponseEntity.status(201).body(commentService.getComment(id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOneArticle(@PathVariable(value = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    return ResponseEntity.status(201).body(articleService.getOneArticle(id,authenticatedUser));
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
    articleService.deleteArticle(id, authenticateUser);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/like")
  public ResponseEntity<?> likeArticle(@PathVariable(value = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    Long userId = authenticatedUser.getId();
    return ResponseEntity.status(201).body(articleService.likeArticle(id, userId));
  }

  @DeleteMapping("/{id}/like")
  public ResponseEntity<?> deleteLike(@PathVariable(value = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    Long userId = authenticatedUser.getId();
    return ResponseEntity.status(201).body(articleService.deleteLike(id, userId));
  }

  @PostMapping("/{id}/favor")
  public ResponseEntity<?> favorArticle(@PathVariable(value = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    Long userId = authenticatedUser.getId();
    return ResponseEntity.status(201).body(articleService.favorArticle(id, userId));
  }

  @DeleteMapping("/{id}/favor")
  public ResponseEntity<?> deleteFavor(@PathVariable(value = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    Long userId = authenticatedUser.getId();
    return ResponseEntity.status(201).body(articleService.deleteFavor(id, userId));
  }
}
