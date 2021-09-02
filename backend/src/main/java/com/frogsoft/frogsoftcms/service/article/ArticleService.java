package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.EntityModel;

public interface ArticleService {

  EntityModel<ArticleDto> saveArticles(ArticleRequest articleRequest, User authenticatedUser);

  EntityModel<ArticleDto> getOneArticle(Long id);
}
