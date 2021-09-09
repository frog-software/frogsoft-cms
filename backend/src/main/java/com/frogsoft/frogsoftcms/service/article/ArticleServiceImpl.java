package com.frogsoft.frogsoftcms.service.article;

import com.frogsoft.frogsoftcms.controller.v1.request.article.ArticleRequest;
import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleMeDto;
import com.frogsoft.frogsoftcms.exception.article.ArticleNotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.conflict.ConflictException;
import com.frogsoft.frogsoftcms.exception.basic.forbidden.ForbiddenException;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.article.Status;
import com.frogsoft.frogsoftcms.model.history.History;
import com.frogsoft.frogsoftcms.model.user.Roles;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.history.HistoryRepository;
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
  private final HistoryRepository historyRepository;
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
  public EntityModel<ArticleMeDto> getOneArticle(Long id, String role, Long userId) {
    Article article;
    User user = userRepository.findById(userId).orElse(null);
    if (role.equals("admin")) {
      article = articleRepository.findById(id)
          .orElseThrow(() -> new ArticleNotFoundException(id));
    } else {
      article = articleRepository.findByIdAndStatus(id, user, Status.NORMAL)
          .orElseThrow(() -> new ArticleNotFoundException(id));
    }
    article.setViews(article.getViews() + 1);
    History history = new History();
    history.setArticle(article)
        .setTime(LocalDateTime.now())
        .setUser(user);
    historyRepository.save(history);
    Article newArticle = articleRepository.save(article);
    System.out.println(newArticle);
    return articleModelAssembler.toMeModel(articleMapper.toArticleMeDto(newArticle, user));
  }

  @Override
  public EntityModel<ArticleDto> editArticle(Long id, User authenticateUser,
      ArticleRequest articleRequest) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    if (!authenticateUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())) {
      if (!authenticateUser.getId().equals(article.getAuthor().getId())) {
        throw new ForbiddenException("无权限修改");
      }
    }
    Article newArticle = articleRepository.save(article
        .setTitle(articleRequest.getTitle())
        .setContent(articleRequest.getContent())
        .setDescription(articleRequest.getDescription())
        .setStatus(articleRequest.getStatus())
        .setCover(articleRequest.getCover())
        .setUpdateDate(LocalDateTime.now()));
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public void deleteArticle(Long id, User authenticateUser) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    if (!authenticateUser.getRoles().contains(Roles.ROLE_ADMIN.getRole())) {
      if (!authenticateUser.getUsername().equals(article.getAuthor().getUsername())) {
        throw new ForbiddenException("无权限删除该文章");
      }
    }
    articleRepository.delete(article);
  }

  @Override
  public EntityModel<ArticleDto> likeArticle(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    User likedUser = userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId));
    if (article.getLikes().contains(likedUser)) {
      throw new ConflictException("该用户已点赞");
    }
    article.getLikes().add(likedUser);
    likedUser.getLikeArticles().add(article);
    userRepository.save(likedUser);
    article.setLikesNum(article.getLikesNum() + 1);
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public EntityModel<ArticleDto> deleteLike(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    User unlikedUser = userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId));
    if (!article.getLikes().contains(unlikedUser)) {
      throw new ConflictException("该用户未点赞，无法取消");
    }
    article.getLikes().remove(unlikedUser);
    article.setLikesNum(article.getLikesNum() - 1);
    Article newArticle = articleRepository.save(article);
    unlikedUser.getLikeArticles().remove(article);
    userRepository.save(unlikedUser);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public EntityModel<ArticleDto> favorArticle(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    User favoredUser = userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId));
    if (article.getFavorites().contains(favoredUser)) {
      throw new ConflictException("该用户已收藏本文章");
    }
    article.getFavorites().add(favoredUser);
    article.setFavoritesNum(article.getFavoritesNum() + 1);
    favoredUser.getFavoriteArticles().add(article);
    userRepository.save(favoredUser);
    Article newArticle = articleRepository.save(article);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public EntityModel<ArticleDto> deleteFavor(Long id, Long userId) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ArticleNotFoundException(id));
    User unfavoredUser = userRepository
        .findById(userId)
        .orElseThrow(() -> new ArticleNotFoundException(userId));
    if (!article.getFavorites().contains(unfavoredUser)) {
      throw new ConflictException("该用户未收藏本文章，无法取消");
    }
    article.getFavorites().remove(unfavoredUser);
    article.setFavoritesNum(article.getFavoritesNum() - 1);
    Article newArticle = articleRepository.save(article);
    unfavoredUser.getFavoriteArticles().remove(article);
    userRepository.save(unfavoredUser);
    return articleModelAssembler.toModel(articleMapper.toArticleDto(newArticle));
  }

  @Override
  public PagedModel<EntityModel<ArticleDto>> findAll(Long userId, String role, String sortBy,
      String order,
      Pageable pageable) {
    if (role.equals("admin")) {
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository.findAllASCAdmin(sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository.findAllDESCAdmin(sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }
    } else {
      User user = userRepository.findById(userId).orElse(null);
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository
            .findAllASC(user, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findAllDESC(user, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }
    }

  }

  @Override
  public PagedModel<EntityModel<ArticleDto>> findBySearch(Long userId, String search, String role,
      String sortBy,
      String order, Pageable pageable) {
    if (role.equals("admin")) {
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository
            .findBySearchASCAdmin(search, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findBySearchDESCAdmin(search, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }

    } else {
      User user = userRepository.findById(userId).orElse(null);
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository
            .findBySearchASC(user, search, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findBySearchDESC(user, search, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }
    }
  }

  public PagedModel<?> findByAuthor(Long userId, String authorName, String role, String sortBy,
      String order,
      Pageable pageable) {
    User author = userRepository.findByUsername(authorName);
    if (role.equals("admin")) {
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository.findByAuthorASCAdmin(author, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findByAuthorDESCAdmin(author, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }
    } else {
      User user = userRepository.findById(userId).orElse(null);
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository
            .findByAuthorASC(user, author, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findByAuthorDESC(user, author, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }
    }
  }

  public PagedModel<?> findBySearchAndAuthor(Long userId, String search, String authorName,
      String role,
      String sortBy, String order, Pageable pageable) {
    User author = userRepository.findByUsername(authorName);
    if (role.equals("admin")) {
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository
            .findBySearchAndAuthorASCAdmin(search, author, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findBySearchAndAuthorDESCAdmin(search, author, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }

    } else {
      User user = userRepository.findById(userId).orElse(null);
      if (order.equals("ASC")) {
        Page<ArticleDto> articles = articleRepository
            .findBySearchAndAuthorASC(user, search, author, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      } else {
        Page<ArticleDto> articles = articleRepository
            .findBySearchAndAuthorDESC(user, search, author, Status.NORMAL, sortBy, pageable)
            .map(articleMapper::toArticleDto);
        return pagedResourcesAssembler.toModel(articles, articleModelAssembler);
      }
    }

  }
}
