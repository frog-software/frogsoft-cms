package com.frogsoft.frogsoftcms.repository.article;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.article.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.frogsoft.frogsoftcms.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Page<Article> findAllByStatus(Status status, Pageable pageable);

  @Query("select A from Article A where (A.content like %?1% or A.title like %?1% or A.description like %?1%) and A.status=?2")
  Page<Article> findBySearch(String search, Status status, Pageable pageable);
}
