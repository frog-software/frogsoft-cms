package com.frogsoft.frogsoftcms.service.user;

import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface UserService {

  PagedModel<EntityModel<User>> getAllUsers(Pageable pageable);

  EntityModel<User> getOneUser(String username);
}
