package com.frogsoft.frogsoftcms.repository.article;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.article.Status;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Optional<Article> findByIdAndStatus(Long id, Status status);

  @Query("select A from Article A where A.status=?1 order by ?2 DESC")
  Page<Article> findAllDESC(Status status, String sortBy, Pageable pageable);

  @Query("select A from Article A where A.status=?1 order by ?2 ASC")
  Page<Article> findAllASC(Status status, String sortBy, Pageable pageable);

  @Query("select A from Article A where (A.content like %?1% or A.title like %?1% or A.description like %?1%) and A.status=?2 order by ?3 DESC")
  Page<Article> findBySearchDESC(String search, Status status, String sortBy, Pageable pageable);

  @Query("select A from Article A where (A.content like %?1% or A.title like %?1% or A.description like %?1%) and A.status=?2 order by ?3 ASC")
  Page<Article> findBySearchASC(String search, Status status, String sortBy, Pageable pageable);
}
