package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comment")
public class CommentController {

  private final CommentService commentService;
}
