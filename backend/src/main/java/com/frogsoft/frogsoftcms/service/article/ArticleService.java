package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.EntityModel;

public interface ArticleService {

  EntityModel<ArticleDto> saveArticles(ArticleRequest articleRequest, User authenticatedUser);

  EntityModel<ArticleDto> getOneArticle(Long id);

  EntityModel<ArticleDto> editArticle(Long id, Long userId, ArticleRequest articleRequest);

  boolean deleteArticle(Long id, Long userId);
}
