//+-------------------------------------------------------------------------
//
//  // ---- What is this file about? --- //
//
//  File:       article.ts
//
//  Directory:  src/services
//
//  History:    9月-05-2021   QQK  Created
//
//--------------------------------------------------------------------------

import http                                     from 'utils/http';
import { pagedModelSimplifier }                 from 'utils/common';
import { JavaPagedModel, SimplifiedPagedModel } from 'types/common';
import { User }                                 from 'types/user';
import { QueryFunctionContext }                 from 'react-query';
import { Article }        from 'types/article';
import { Badge, message } from 'antd';
import React              from 'react';

const ARTICLE_V1_URL = '/v1/articles';

export const getArticleList = (queryContext: QueryFunctionContext<any, any>) => {
  const [, page, size] = queryContext.queryKey;
  return new Promise<SimplifiedPagedModel<Article>>((resolve, reject) => {
    http.get<JavaPagedModel<Article>>(ARTICLE_V1_URL, { page, size })
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
