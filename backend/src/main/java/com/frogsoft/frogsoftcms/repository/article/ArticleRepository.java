package com.frogsoft.frogsoftcms.repository.article;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
