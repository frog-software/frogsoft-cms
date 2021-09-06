package com.frogsoft.frogsoftcms.service.home;

import com.frogsoft.frogsoftcms.controller.v1.request.home.AnnouncementsSetRequest;
import com.frogsoft.frogsoftcms.dto.assembler.article.ArticleModelAssembler;
import com.frogsoft.frogsoftcms.dto.mapper.article.ArticleMapper;
import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import com.frogsoft.frogsoftcms.exception.article.ArticleNotFoundException;
import com.frogsoft.frogsoftcms.exception.basic.notfound.NotFoundException;
import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.config.Config;
import com.frogsoft.frogsoftcms.model.user.User;
import com.frogsoft.frogsoftcms.repository.article.ArticleRepository;
import com.frogsoft.frogsoftcms.repository.config.ConfigRepository;
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


import java.util.*;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {

  private final ArticleRepository articleRepository;
  private final ArticleModelAssembler articleModelAssembler;
  private final ArticleMapper articleMapper;
  private final UserRepository userRepository;
  private final ConfigRepository configRepository;


  /**
   * getAllArticleDtos 获得所有文章的Dto列表
   * @return List<ArticleDto>类型
   */
  private List<ArticleDto> getAllArticleDtos() {
    return articleRepository.findAll().stream().map(
        articleMapper::toArticleDto
    ).collect(Collectors.toList());
  }

  /**
   * getRecommendations 返回推荐文章列表的CollectionModel
   * @param authenticatedUser 登陆的用户，该参数暂不使用，用于后续个性化推荐
   * @return CollectionModel<EntityModel<ArticleDto>>
   */
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
    return articleModelAssembler.toCollectionModel(retArticles);
  }

  /**
   * getDailyArticle 返回一个管理员设置的每日推荐文章的EntityModel
   * @return EntityModel<ArticleDto>
   */
  @Override
  public EntityModel<ArticleDto> getDailyArticle() {

    // 获取每日推荐文章的id
    String dailyPickup = configRepository.findByConfigKey("DailyPickup").getConfigValue();
    Long articlePickupId = Long.parseLong(dailyPickup);
    Optional<Article> articlePickup = articleRepository.findById(articlePickupId);

    if (articlePickup.isEmpty()) {
      throw new ArticleNotFoundException(articlePickupId);
    }

    return articleModelAssembler.toModel(articleMapper.toArticleDto(articlePickup.get()));
  }

  /**
   * changeDailyArticle 设置每日推荐文章（仅限管理员）
   * @param articleId 要设置的文章id
   * @param authenticatedUser 设置人
   * @return EntityModel<ArticleDto> 设置好的推荐文章的数据模型
   */
  @Override
  public EntityModel<ArticleDto> changeDailyArticle(Integer articleId, User authenticatedUser) {
    Long articlePickupId = articleId.longValue();
    Config dailyPickupConfig = configRepository.findByConfigKey("DailyPickup");

    Optional<Article> articlePickupNew = articleRepository.findById(articlePickupId);
    if(articlePickupNew.isEmpty()){
      throw new ArticleNotFoundException(articlePickupId);
    }

    dailyPickupConfig = configRepository.save(
        dailyPickupConfig.setConfigValue(articleId.toString())
    );

    return articleModelAssembler.toModel(articleMapper.toArticleDto(articlePickupNew.get()));
  }

  /**
   * getRankList 返回全站文章排行榜（数据模型列表）
   * @return CollectionModel<EntityModel<ArticleDto>>
   */
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

  /**
   * getAnnouncements 获取管理员设置公告文章的数据模型列表
   * @return CollectionModel<EntityModel<ArticleDto>>
   */
  @Override
  public CollectionModel<EntityModel<ArticleDto>> getAnnouncements(){
    String announcements = configRepository.findByConfigKey("AnnouncementsId").getConfigValue();

    if(announcements.equals("")){
      throw new NotFoundException("暂无公告");
    }

    List<Long> announcementIds = Arrays.stream(announcements.split(",")).map(
        Long::parseLong
    ).collect(Collectors.toList());

    List<Optional<Article>> announceArticles = new ArrayList<>();

    for (Long announcementId:announcementIds
    ) {
      Optional<Article> _announceArticle = articleRepository.findById(announcementId);
      if(_announceArticle.isEmpty()) {
        throw new ArticleNotFoundException(announcementId);
      }
      announceArticles.add(_announceArticle);
    }

    List<ArticleDto> retArticles = new ArrayList<>();
    for (Optional<Article> announceArticle : announceArticles) {
      announceArticle.ifPresent(article -> retArticles.add(articleMapper.toArticleDto(article)));
    }

    return articleModelAssembler.toCollectionModel(retArticles);
  }

  /**
   * changeAnnouncements 设置公告文章的id列表（仅限管理员）
   * @param announcementsSetRequest 要设置公告文章的id（请求类）
   * @param authenticatedUser 设置人
   * @return CollectionModel<EntityModel<ArticleDto>> 设置好的对应的公告文章数据模型列表
   */
  @Override
  public CollectionModel<EntityModel<ArticleDto>> changeAnnouncements(
      AnnouncementsSetRequest announcementsSetRequest, User authenticatedUser) {
    StringBuilder announcements = new StringBuilder();
    Config announcementsConfig = configRepository.findByConfigKey("AnnouncementsId");

    List<Optional<Article>> announceArticles = new ArrayList<>();

    for (Long announcementId:announcementsSetRequest.getArticleIds()
    ) {
      Optional<Article> _announceArticle = articleRepository.findById(announcementId);
      if(_announceArticle.isEmpty()) {
        throw new ArticleNotFoundException(announcementId);
      }
      announceArticles.add(_announceArticle);
      announcements.append(announcementId).append(",");
    }

    announcementsConfig = configRepository.save(
        announcementsConfig.setConfigValue(announcements.toString())
    );

    List<ArticleDto> retArticles = new ArrayList<>();
    for (Optional<Article> announceArticle : announceArticles) {
      announceArticle.ifPresent(article -> retArticles.add(articleMapper.toArticleDto(article)));
    }

    return articleModelAssembler.toCollectionModel(retArticles);
  }
}
