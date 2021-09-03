package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comments")
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getComment(@PathVariable(name = "id") Long id,
      @AuthenticationPrincipal User authenticatedUser){
    return ResponseEntity.status(201).body(commentService.get(id, authenticatedUser));
  }
}
