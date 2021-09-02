package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.article.Status;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleModelAssembler articleModelAssembler;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;

  @Override
  public EntityModel<ArticleDto> saveArticles(ArticleRequest articleRequest,
      User authenticatedUser) {
    Article article = articleRepository.save(new Article()
        .setContent(articleRequest.getContent())
        .setTitle(articleRequest.getTitle())
        .setDescription(articleRequest.getDescription())
        .setCover(articleRequest.getCover())
        .setAuthor(userRepository.findByUsername(authenticatedUser.getUsername()))
        .setPublishDate(LocalDateTime.now())
        .setUpdateDate(LocalDateTime.now())
        .setStatus(Status.Normal)
        .setViews(0));
    return articleModelAssembler.toModel(articleMapper.toArticleDto(article));
  }

  @Override
  public EntityModel<ArticleDto> getOneArticle(Long id) {
    Article article = articleRepository.findByid(id);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(article));
  }


}