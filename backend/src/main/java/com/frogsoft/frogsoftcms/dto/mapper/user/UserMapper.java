package com.frogsoft.frogsoftcms.dto.mapper.user;

import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.mapper.comment.CommentMapper;
import com.frogsoft.frogsoftcms.dto.model.user.UserDetailDto;
import com.frogsoft.frogsoftcms.dto.model.user.UserDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.comment.CommentRepository;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDto toUserDto(User user) {

    return new UserDto()
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setRoles(user.getRoles());
  }
}
