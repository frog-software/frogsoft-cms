package com.frogsoft.frogsoftcms.repository.comment;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.commment.Comment;
import com.frogsoft.frogsoftcms.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByArticle(Article article);
  void deleteAllByParent(Comment comment);
  List<Comment> findByArticleAndAuthor(Article article, User author);
  List<Comment> findByAuthor(User author);
}
