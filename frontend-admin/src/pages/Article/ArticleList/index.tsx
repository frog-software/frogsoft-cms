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

import React, { FC, useEffect, useState }                                   from 'react';
import { Article }                                                          from 'types/article';
import Block                                                                from 'components/Block';
import {
  Badge, Button, Col, notification, Popconfirm, Row, Space, Table,
} from 'antd';
import { useQuery }                                                         from 'react-query';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useHistory }                                                       from 'react-router';
import { deleteArticle, getArticleList }                                    from 'services/article';
import Search                                                               from 'antd/es/input/Search';

const ArticleList: FC = () => {
  const [articleList, setArticleList]     = useState<Article[]>();
  const [currentPage, setCurrentPage]     = useState<number>(1);
  const [pageSize, setPageSize]           = useState<number>(10);
  const [totalItems, setTotalItems]       = useState<number>();
  const [deleteLoading, setDeleteLoading] = useState<boolean>(false);
  const history                           = useHistory();

  const {
    isLoading, data, refetch, remove,
  } = useQuery(
    ['articleList', currentPage - 1, pageSize],
    getArticleList,
    {
      staleTime: 5000,
      onError: (err) => {
        notification['error']({ message: String(err) });
      },
    },
  );

  const tableColumns = [
    {
      key: 'id',
      dataIndex: 'id',
      title: '文章ID',
      sorter: {
        compare: (a, b) => a.id - b.id,
        multiple: 2,
      },
    },
    {
      key: 'title',
      dataIndex: 'title',
      title: '标题',
    },
    {
      key: 'author',
      dataIndex: ['author', 'username'],
      title: '作者',
    },
    {
      key: 'publishDate',
      dataIndex: 'publishDate',
      title: '发布时间',
      sorter: {
        compare: (a, b) => a.publishDate - b.publishDate,
        multiple: 1,
      },
    },
    {
      key: 'status',
      dataIndex: 'status',
      title: '状态',
    },
    {
      key: 'action',
      title: '执行操作',
      render: (article) => (
        <Space>
          <Button
            onClick={() => {
              history.push(`/articles/${article.id}`);
            }}
            type="text"
            style={{ color: 'var(--primary-color)' }}
          >
            查看详情
          </Button>
          <Popconfirm
            title="确定删除该文章吗？删除之后不可恢复！"
            okText="确定"
            cancelText="取消"
            onConfirm={() => {
              setDeleteLoading(true);
              deleteArticle(article.id)
                .then(() => {
                  notification['success']({ message: '文章删除成功' });
                  remove();
                  refetch({
                    throwOnError: true,
                    cancelRefetch: false,
                  });
                }).catch((error) => {
                  notification['error']({ message: '文章删除失败', description: String(error) });
                }).finally(() => {
                  setDeleteLoading(false);
                });
            }}
          >
            <Button danger type="text">删除文章</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  useEffect(() => {
    if (!data) return;

    const tableList = data.list?.map((i) => ({
      ...i,
      status: i.status === 'NORMAL' ? (
        <Badge status="processing" color="green" text="正常" />
      ) : (
        <Badge status="default" color="gray" text="屏蔽" />
      ),
    }));

    setArticleList(tableList as any);
    setTotalItems(data.page.total);
  }, [data]);

  return (
    <>
      <Block title="文章列表">
        <Row>
          <Col span={4} offset={20}>
            <Search
              placeholder="输入文章搜索关键字"
              onSearch={() => (console.log('搜索文章'))}
              enterButton
              style={{ marginBottom: '16px' }}
            />
          </Col>
          <Col span={24}>
            <Table
              rowKey="id"
              loading={isLoading || deleteLoading}
              columns={tableColumns}
              dataSource={articleList}
              pagination={{
                pageSize,
                total: totalItems,
                showSizeChanger: true,
                current: currentPage,
                onChange: (page, size) => {
                  setCurrentPage(size === pageSize ? page : 1);
                  setPageSize(size);
                },
              }}
            />
          </Col>
        </Row>
      </Block>
    </>
  );
};

export default ArticleList;
