//+-------------------------------------------------------------------------
//
//  文章
//
//  File:       index.tsx
//
//  Directory:  src/pages/Article
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, Suspense } from 'react';
import Preview                 from 'components/Preview';
import { Route }                 from 'react-router-dom';

const ArticleList = React.lazy(() => import('./ArticleList'));
const ArticleInfo = React.lazy(() => import('./ArticleInfo'));

const ArticlePage: FC = () => (
  <Suspense fallback={<Preview />}>
    <Route exact path="/articles" component={ArticleList} />
    <Route exact path="/articles/:id" component={ArticleInfo} />
  </Suspense>
);

export default ArticlePage;
