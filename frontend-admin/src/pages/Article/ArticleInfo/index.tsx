//+-------------------------------------------------------------------------
//
//  文章详细信息
//
//  File:       index.tsx
//
//  Directory:  src/pages/Article/ArticleInfo
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useState } from 'react';
import { Article }             from 'types/article';
import Title                   from 'antd/es/typography/Title';

const ArticleInfo: FC = () => {
  const [articleInfo, setAricleInfo] = useState<Article>();

  return (
    <>
      <Title>Hello ArticleInfo</Title>
    </>
  );
};

export default ArticleInfo;