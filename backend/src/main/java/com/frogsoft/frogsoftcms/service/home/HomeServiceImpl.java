package com.frogsoft.frogsoftcms.service.home;

import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {

  private final ArticleRepository articleRepository;
  private final ArticleModelAssembler articleModelAssembler;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;


  private List<ArticleDto> getAllArticleDtos() {
    return articleRepository.findAll().stream().map(
        articleMapper::toArticleDto
    ).collect(Collectors.toList());
  }

  @Override
  public CollectionModel<EntityModel<ArticleDto>> getRecommendations(User authenticatedUser) {
    List<ArticleDto> articles = getAllArticleDtos();
    List<ArticleDto> retArticles = new ArrayList<>();
    // 随机获取所有文章列表的子集
    Random randArt = new Random();
    for (int i = 0; i <= randArt.nextInt(articles.size()); i++) {
      ArticleDto pickup = articles.get(randArt.nextInt(articles.size()));
      retArticles.add(pickup);
      articles.remove(pickup);
    }
    // TODO：筛选和获取推荐的文章Dto对象
    return articleModelAssembler.toCollectionModel(retArticles);
  }

  @Override
  public EntityModel<ArticleDto> getDailyArticle() {
    Random randArt = new Random();

    List<ArticleDto> articles = getAllArticleDtos();

    if (articles.size() <= 0) {
      throw new NotFoundException("目前暂无文章");
    }
    // 获取一个随机文章作为每日推荐
    return articleModelAssembler.toModel(articles.get(randArt.nextInt(articles.size())));
  }

  @Override
  public CollectionModel<EntityModel<ArticleDto>> getRankList() {
    List<Article> allArticles = articleRepository.findAll();

    if (allArticles.size() <= 0) {
      throw new NotFoundException("目前暂无文章");
    }

    Map<Article, Long> articleMap = new HashMap<Article, Long>();

    for (Article article : allArticles
    ) {
      // Rank算法：目前按浏览量排序
      // 高级算法：[ 浏览量*（点赞+收藏）] / [发布到当前的时间（min） + 2] ^ G(1.5)
      long rank = article.getViews();
      articleMap.put(article, rank);
    }

    List<Map.Entry<Article, Long>> unrankedArticleMap =
        new ArrayList<Map.Entry<Article, Long>>(articleMap.entrySet());

    unrankedArticleMap.sort(new Comparator<Map.Entry<Article, Long>>() {
      @Override
      public int compare(Map.Entry<Article, Long> o1, Map.Entry<Article, Long> o2) {
        return (o2.getValue().intValue() - o1.getValue().intValue());
      }
    });

    List<ArticleDto> rankedArticleList = new ArrayList<>();
    for (Map.Entry<Article, Long> articleLongEntry : unrankedArticleMap) {
      rankedArticleList.add(
          articleMapper.toArticleDto(articleLongEntry.getKey())
      );
    }

    return articleModelAssembler.toCollectionModel(rankedArticleList);

  }
}
