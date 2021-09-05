package com.frogsoft.frogsoftcms.service.home;

import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {

  private final ArticleRepository articleRepository;
  private final ArticleModelAssembler articleModelAssembler;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;

  @Override
  public CollectionModel<EntityModel<ArticleDto>> getRecommendations(User authenticatedUser){
    List<ArticleDto> articles = articleRepository.findAll().stream().map(
            articleMapper::toArticleDto
    ).collect(Collectors.toList());
    // TODO：筛选和获取推荐的文章Dto对象
    return articleModelAssembler.toCollectionModel(articles);
  }

  @Override
  public EntityModel<ArticleDto> getDailyArticle(){
    Random randArt = new Random();
    List<ArticleDto> articles = articleRepository.findAll().stream().map(
            articleMapper::toArticleDto
    ).collect(Collectors.toList());

    if(articles.size() <= 0){
      throw new NotFoundException("目前暂无文章");
    }
    // 获取一个随机文章作为每日推荐
    return articleModelAssembler.toModel(articles.get(randArt.nextInt(articles.size())));
  }
}
