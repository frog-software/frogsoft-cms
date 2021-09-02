package com.frogsoft.frogsoftcms.dto.assembler.article;

import com.frogsoft.frogsoftcms.dto.model.article.ArticleDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ArticleModelAssembler implements
    RepresentationModelAssembler<ArticleDto, EntityModel<ArticleDto>> {

  @Override
  public EntityModel<ArticleDto> toModel(ArticleDto articleDto) {
    return EntityModel.of(
        articleDto
    );
  }

}
