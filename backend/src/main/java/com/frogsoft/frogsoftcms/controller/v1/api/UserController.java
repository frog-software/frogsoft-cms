package com.frogsoft.frogsoftcms.controller.v1.api;

import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.service.user.UserService;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;


  @RolesAllowed("ROLE_ADMIN")
  @GetMapping("")
  // can be replaced by <?>
  public ResponseEntity<PagedModel<EntityModel<User>>> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok().body(userService.getAllUsers(PageRequest.of(page, size)));
  }


  @GetMapping("/{username}")
  public ResponseEntity<EntityModel<User>> getOneUser(
      @PathVariable(value = "username") String username) {

    return ResponseEntity.ok().body(userService.getOneUser(username));
  }
}
