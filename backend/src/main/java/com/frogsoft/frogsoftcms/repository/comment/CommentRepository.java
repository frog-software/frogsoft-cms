package com.frogsoft.frogsoftcms.repository.comment;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByArticle(Article article);
  void deleteAllByParent(Comment comment);
}
