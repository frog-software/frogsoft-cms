package com.frogsoft.frogsoftcms.service.home;

import com.frogsoft.frogsoftcms.controller.v1.request.home.AnnouncementsSetRequest;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.model.user.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface HomeService {
  
  CollectionModel<EntityModel<ArticleDto>> getRecommendations(User authenticatedUser);
  
  CollectionModel<EntityModel<ArticleDto>> getRankList();
  
  EntityModel<ArticleDto> getDailyArticle();

  EntityModel<ArticleDto> changeDailyArticle(Integer articleId, User authenticatedUser);

  CollectionModel<EntityModel<ArticleDto>> getAnnouncements();

  CollectionModel<EntityModel<ArticleDto>> changeAnnouncements(
      AnnouncementsSetRequest announcementsSetRequest, User authenticatedUser);




}
