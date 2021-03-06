package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comments")
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getCommentByDetail(@PathVariable(name = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    return ResponseEntity.status(201).body(commentService.getByDetail(id, authenticatedUser));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> putComment(@PathVariable(name = "id") Long id,
      @RequestBody String content,
      @AuthenticationPrincipal User authenticatedUser) {
    return ResponseEntity.status(201)
        .body(commentService.changeContent(id, content, authenticatedUser));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser) {
    commentService.delete(id, authenticatedUser);
    return ResponseEntity.status(200).body(ResponseEntity.EMPTY.getBody());
  }

  @GetMapping("")
  public ResponseEntity<?> getComment(@RequestParam("article") Long id,
      @RequestParam("username") String username) {
    return ResponseEntity.status(201)
        .body(commentService.getComment(id, username));
  }
}
