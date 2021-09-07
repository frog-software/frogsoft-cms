package com.frogsoft.frogsoftcms.repository.user;

import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findByUsername(String username);

  Page<User> findAllBy(Pageable pageable);

}
