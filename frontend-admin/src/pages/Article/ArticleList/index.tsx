//+-------------------------------------------------------------------------
//
//  文章列表
//
//  File:       index.tsx
//
//  Directory:  src/pages/Article/ArticleList
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useState } from 'react';
import { Article }             from 'types/article';
import Block                   from 'components/Block';
import { Table }                 from 'antd';

const tableColumns = [
  {
    key: 'id',
    data: 'id',
    title: '文章ID',
  },
  {
    key: 'title',
    data: 'title',
    title: '标题',
  },
  {
    key: 'author',
    data: 'author',
    title: '作者',
  },
  {
    key: 'publishDate',
    data: 'publishDate',
    title: '发布时间',
  },
];

const ArticleList: FC = () => {
  const [articleList, setAricleList] = useState<Article[]>();
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [pageSize, setPageSize]       = useState<number>(10);
  const [totalItems, setTotalItems]   = useState<number>();

  return (
    <>
      <Block title="文章管理">
        <Table
          rowKey="id"
          columns={tableColumns}
        />
      </Block>
    </>
  );
};

export default ArticleList;
