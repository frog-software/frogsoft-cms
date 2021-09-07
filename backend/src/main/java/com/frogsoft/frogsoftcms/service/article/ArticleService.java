package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface ArticleService {

  EntityModel<ArticleDto> saveArticles(ArticleRequest articleRequest, User authenticatedUser);

  EntityModel<ArticleDto> getOneArticle(Long id,User authenticatedUser);

  EntityModel<ArticleDto> editArticle(Long id, Long userId, ArticleRequest articleRequest);

  void deleteArticle(Long id, User authenticateUser);

  EntityModel<ArticleDto> likeArticle(Long id, Long userId);

  EntityModel<ArticleDto> deleteLike(Long id, Long userId);

  EntityModel<ArticleDto> favorArticle(Long id, Long userId);

  EntityModel<ArticleDto> deleteFavor(Long id, Long userId);

  PagedModel<EntityModel<ArticleDto>> findAll(String sortBy, String order, Pageable pageable);

  PagedModel<EntityModel<ArticleDto>> findBySearch(String search, String sortBy, String order,
      Pageable pageable);
}
