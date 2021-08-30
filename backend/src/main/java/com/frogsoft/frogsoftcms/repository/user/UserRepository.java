package com.frogsoft.frogsoftcms.repository.user;

import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
}
