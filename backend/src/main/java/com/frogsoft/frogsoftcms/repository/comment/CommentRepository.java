package com.frogsoft.frogsoftcms.repository.comment;

import com.frogsoft.frogsoftcms.model.commment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
