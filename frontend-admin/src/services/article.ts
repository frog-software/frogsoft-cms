//+-------------------------------------------------------------------------
//
//  公告
//
//  File:       announcement.ts
//
//  Directory:  src/services
//
//  History:    Sep-09-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import http                                     from 'utils/http';
import { pagedModelSimplifier }                 from 'utils/common';
import { JavaPagedModel, SimplifiedPagedModel } from 'types/common';
import { QueryFunctionContext }                 from 'react-query';
import { Article }                              from 'types/article';

const ARTICLE_V1_URL = '/v1/articles';

export const getArticleList = (queryContext: QueryFunctionContext<any, any>) => {
  const [, page, size, search] = queryContext.queryKey;
  return new Promise<SimplifiedPagedModel<Article>>((resolve, reject) => {
    http.get<JavaPagedModel<Article>>(ARTICLE_V1_URL, { page, size, search })
      .then((res) => {
        const simplifiedModel = pagedModelSimplifier(res);
        resolve(simplifiedModel);
      })
      .catch(reject);
  });
};

// 删除文章
export const deleteArticle = (articleId: number) => http.del(`/v1/articles/${articleId}`);

export default {
  getArticleList,
  deleteArticle,
};
