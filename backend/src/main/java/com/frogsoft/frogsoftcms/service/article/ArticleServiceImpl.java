package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.exception.article.ArticleNotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.forbidden.ForbiddenException;
import com.frogsoft.frogsoftcms.model.article.Article;
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
        .setStatus(articleRequest.getStatus())
        .setViews(0));
    return articleModelAssembler.toModel(articleMapper.toArticleDto(article));
  }

  @Override
  public EntityModel<ArticleDto> getOneArticle(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    return articleModelAssembler.toModel(articleMapper.toArticleDto(article));
  }

  @Override
  public EntityModel<ArticleDto> editArticle(Long id, Long userId, ArticleRequest articleRequest) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));

    if (userId.equals(article.getAuthor().getId())) {
      Article newArticle = articleRepository.save(article
          .setTitle(articleRequest.getTitle())
          .setContent(articleRequest.getContent())
          .setDescription(articleRequest.getDescription())
          .setStatus(articleRequest.getStatus())
          .setCover(articleRequest.getCover()));
      return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
    } else {
      throw new ForbiddenException("无权限修改");
    }
  }

  @Override
  public void deleteArticle(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    if (userId.equals(article.getAuthor().getId())) {
      articleRepository.delete(article);
    } else {
      throw new ForbiddenException("无权限删除改文章");
    }
  }

  @Override
  public EntityModel<ArticleDto> likeArticle(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    article.getLikes().add(userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId)));
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public EntityModel<ArticleDto> deleteLike(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    article.getLikes().remove(userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId)));
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }
}
