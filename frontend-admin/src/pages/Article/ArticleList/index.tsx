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

import React, { FC, useEffect, useState } from 'react';
import { Article }                        from 'types/article';
import Block                              from 'components/Block';
import {
  Badge,
  Button, Col, notification, Popconfirm, Row, Space, Table,
}                                        from 'antd';
import { useQuery }                      from 'react-query';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useHistory }                    from 'react-router';
import { getArticleList, deleteArticle } from 'services/article';
import Search                            from 'antd/es/input/Search';
import http                              from 'utils/http';
import { StarFilled }                    from '@ant-design/icons';

const ArticleList: FC = () => {
  const [articleList, setArticleList]               = useState<Article[]>();
  const [currentPage, setCurrentPage]               = useState<number>(1);
  const [pageSize, setPageSize]                     = useState<number>(10);
  const [totalItems, setTotalItems]                 = useState<number>();
  const [loading, setLoading]           = useState<boolean>(false);
  const history                                     = useHistory();
  const [recommendArticleId, setRecommendArticleId] = useState<number>();
  const [render, setRender]             = useState<boolean>(false);

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

  // 编辑每日推荐文章
  const handleSetRecommend = (articleId: number) => {
    setLoading(true);

    const tempData = { articleId };

    if (recommendArticleId === articleId) {
      tempData.articleId = 0;
      http.put('/v1/home/daily', tempData)
        .then(() => {
          notification['success']({ message: '取消每日设置成功' });
          setRecommendArticleId(tempData.articleId);
        })
        .catch((error) => {
          notification['error']({ message: '取消每日推荐设置失败', description: String(error) });
        })
        .finally(() => {
          setRender(!render);
          setLoading(false);
        });
    } else {
      http.put('/v1/home/daily', tempData)
        .then(() => {
          notification['success']({ message: '每日推荐设置成功' });
          setRecommendArticleId(tempData.articleId);
        })
        .catch((error) => {
          notification['error']({ message: '每日推荐设置失败', description: String(error) });
        })
        .finally(() => {
          setRender(!render);
          setLoading(false);
        });
    }
  };

  // 文章列表属性
  const tableColumns = [
    {
      key: 'id',
      dataIndex: 'id',
      title: '文章ID',
      sorter: {
        compare: (a, b) => a.id - b.id,
        multiple: 2,
      },
      width: '90px',
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
      width: '90px',
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
          >
            查看详情
          </Button>
          <Button
            onClick={() => handleSetRecommend(article.id)}
            type="text"
          >
            {
              article.id === recommendArticleId
                ? '取消每日推荐' : '设为每日推荐'
            }
          </Button>
          <Popconfirm
            title="确定删除该文章吗？删除之后不可恢复！"
            okText="确定"
            cancelText="取消"
            onConfirm={() => {
              setLoading(true);
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
                  setLoading(false);
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
    (async () => {
      const recommendArticle = await http.get<Article>('/v1/home/daily');
      setRecommendArticleId(recommendArticle?.id);
    })();
  }, [render]);

  useEffect(() => {
    (async () => {
      if (!data) return;

      const tableList = data.list?.map((i) => ({
        ...i,
        status: i.status === 'NORMAL' ? (
          <Badge status="processing" color="green" text="正常" />
        ) : (
          <Badge status="default" color="gray" text="屏蔽" />
        ),
        title: i.id === recommendArticleId ? (
          <Row>
            <StarFilled style={{ color: 'gold', marginTop: '4px', marginRight: '4px' }} />
            <p>{i.title}</p>
          </Row>
        ) : (
          <p>{i.title}</p>
        ),
      }));

      setArticleList(tableList as any);
      setTotalItems(data.page.total);
    })();
  }, [data, recommendArticleId]);

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
              loading={isLoading || loading}
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
