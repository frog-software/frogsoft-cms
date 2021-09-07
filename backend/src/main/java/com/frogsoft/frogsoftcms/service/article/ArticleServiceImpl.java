package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.exception.article.ArticleNotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.forbidden.ForbiddenException;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.article.Status;
import com.frogsoft.frogsoftcms.model.user.Roles;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleModelAssembler articleModelAssembler;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;
  private final PagedResourcesAssembler<ArticleDto> pagedResourcesAssembler;

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
        .setViews(0)
        .setFavoritesNum(0)
        .setLikesNum(0));
    return articleModelAssembler.toModel(articleMapper.toArticleDto(article));
  }

  @Override
  public EntityModel<ArticleDto> getOneArticle(Long id,User authenticatedUser) {
    Long userId = authenticatedUser.getId();
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    article.setViews(article.getViews()+1);
    if (userRepository != null){
      article.getHistories().add(userRepository
          .findById(userId)
          .orElseThrow(() -> new ArticleNotFoundException(userId)));
    }
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
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
  public void deleteArticle(Long id, User authenticateUser) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    if (!authenticateUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())){
      if (!authenticateUser.equals(article.getAuthor())){
        throw new ForbiddenException("无权限删除该文章");
      }
    }
    articleRepository.delete(article);
  }

  @Override
  public EntityModel<ArticleDto> likeArticle(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    article.getLikes().add(userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId)));
    article.setLikesNum(article.getLikesNum()+1);
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
    article.setLikesNum(article.getLikesNum()-1);
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public EntityModel<ArticleDto> favorArticle(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    article.getFavorites().add(userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId)));
    article.setFavoritesNum(article.getFavoritesNum()+1);
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public EntityModel<ArticleDto> deleteFavor(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    article.getFavorites().remove(userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId)));
    article.setFavoritesNum(article.getFavoritesNum()-1);
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public PagedModel<EntityModel<ArticleDto>> findAll(String sortBy, String order,
      Pageable pageable) {
    if (order.equals("ASC")) {
      Page<ArticleDto> articles = articleRepository.findAllASC(Status.NORMAL, sortBy, pageable)
          .map(articleMapper::toArticleDto);
      return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
    } else {
      Page<ArticleDto> articles = articleRepository.findAllDESC(Status.NORMAL, sortBy, pageable)
          .map(articleMapper::toArticleDto);
      return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
    }

  }

  @Override
  public PagedModel<EntityModel<ArticleDto>> findBySearch(String search, String sortBy,
      String order, Pageable pageable) {
    if (order.equals("ASC")) {
      Page<ArticleDto> articles = articleRepository
          .findBySearchASC(search, Status.NORMAL, sortBy, pageable)
          .map(articleMapper::toArticleDto);
      return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
    } else {
      Page<ArticleDto> articles = articleRepository
          .findBySearchDESC(search, Status.NORMAL, sortBy, pageable)
          .map(articleMapper::toArticleDto);
      return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
    }
  }
}
