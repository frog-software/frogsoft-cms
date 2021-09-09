package com.frogsoft.frogsoftcms.repository.article;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.article.Status;
import com.frogsoft.frogsoftcms.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  @Query("select A from Article A where A.id=?1 and (A.author=?2 or A.status=?3)")
  Optional<Article> findByIdAndStatus(Long id, User user, Status status);

  @Query("select A from Article A where A.author=?1 or A.status=?2 order by ?2 DESC")
  Page<Article> findAllDESC(User user, Status status, String sortBy, Pageable pageable);

  @Query("select A from Article A where A.author=?1 or A.status=?2 order by ?2 ASC")
  Page<Article> findAllASC(User user, Status status, String sortBy, Pageable pageable);

  @Query("select A from Article A where (A.content like %?2% or A.title like %?2% or A.description like %?2%) and (A.status=?3 or A.author=?1) order by ?4 DESC")
  Page<Article> findBySearchDESC(User user, String search, Status status, String sortBy,
      Pageable pageable);

  @Query("select A from Article A where (A.content like %?2% or A.title like %?2% or A.description like %?2%) and (A.status=?3 or A.author=?1) order by ?4 ASC")
  Page<Article> findBySearchASC(User user, String search, Status status, String sortBy,
      Pageable pageable);

  @Query("select A from Article A where (A.content like %?2% or A.title like %?2% or A.description like %?2%) and A.author=?3 and (A.status=?4 or A.author=?1) order by ?5 DESC")
  Page<Article> findBySearchAndAuthorDESC(User user, String search, User author, Status status,
      String sortBy, Pageable pageable);

  @Query("select A from Article A where (A.content like %?2% or A.title like %?2% or A.description like %?2%) and A.author=?3 and (A.status=?4 or A.author=?1) order by ?5 ASC")
  Page<Article> findBySearchAndAuthorASC(User user, String search, User author, Status status,
      String sortBy,
      Pageable pageable);

  @Query("select A from Article A where A.author=?2 and (A.status=?3 or A.author=?1) order by ?4 DESC")
  Page<Article> findByAuthorDESC(User user, User author, Status status, String sortBy,
      Pageable pageable);

  @Query("select A from Article A where A.author=?2 and (A.status=?3 or A.author=?1) order by ?4 ASC")
  Page<Article> findByAuthorASC(User user, User author, Status status, String sortBy,
      Pageable pageable);


  @Query("select A from Article A order by ?1 DESC")
  Page<Article> findAllDESCAdmin(String sortBy, Pageable pageable);

  @Query("select A from Article A order by ?1 ASC")
  Page<Article> findAllASCAdmin(String sortBy, Pageable pageable);

  @Query("select A from Article A where A.content like %?1% or A.title like %?1% or A.description like %?1% order by ?2 DESC")
  Page<Article> findBySearchDESCAdmin(String search, String sortBy, Pageable pageable);

  @Query("select A from Article A where A.content like %?1% or A.title like %?1% or A.description like %?1% order by ?2 ASC")
  Page<Article> findBySearchASCAdmin(String search, String sortBy, Pageable pageable);

  @Query("select A from Article A where A.author=?1 order by ?2 DESC")
  Page<Article> findByAuthorDESCAdmin(User author, String sortBy, Pageable pageable);

  @Query("select A from Article A where A.author=?1 order by ?2 ASC")
  Page<Article> findByAuthorASCAdmin(User author, String sortBy, Pageable pageable);

  @Query("select A from Article A where (A.content like %?1% or A.title like %?1% or A.description like %?1%)and A.author=?2 order by ?3 DESC")
  Page<Article> findBySearchAndAuthorDESCAdmin(String search, User author, String sortBy,
      Pageable pageable);

  @Query("select A from Article A where (A.content like %?1% or A.title like %?1% or A.description like %?1%)and A.author=?2 order by ?3 ASC")
  Page<Article> findBySearchAndAuthorASCAdmin(String search, User author, String sortBy,
      Pageable pageable);

  List<Article> findByAuthor(User user);
}
