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

import React, { FC, useEffect, useState } from 'react';
import { Article }                        from 'types/article';
import Block                              from 'components/Block';
import { useParams }                      from 'react-router-dom';
import http                               from 'utils/http';
import {
  Badge,
  Button, Col, Descriptions, Divider, Image, Row, Space,
  Statistic, Popconfirm, Form, Select, Input, Table, notification,
}                                         from 'antd';
import DescriptionsItem                   from 'antd/es/descriptions/Item';
import {
  CloudOutlined, LikeOutlined, StarOutlined,
}                                         from '@ant-design/icons';
import { useForm }                        from 'antd/es/form/Form';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useHistory }                     from 'react-router';
import TextArea                           from 'antd/es/input/TextArea';
import { deleteArticle }                  from 'services/article';
import { Comment }                        from 'types/comment';
import { JavaCollectionModel }            from 'types/common';
import { collectionModelSimplifier }      from 'utils/common';

const ArticleInfo: FC = () => {
  const params: { id: string }              = useParams();
  const [articleInfo, setArticleInfo]       = useState<Article>();
  const [editDetail, setEditDetail]         = useState<boolean>(false);
  const [editContent, setEditContent]       = useState<boolean>(false);
  const [articleDetail]                     = useForm();
  const [articleContent]                    = useForm();
  const history                             = useHistory();
  const { Option }                            = Select;
  const [detailLoading, setDetailLoading]   = useState<boolean>(false);
  const [contentLoading, setContentLoading] = useState<boolean>(false);
  const [commentLoading, setCommentLoading] = useState<boolean>(false);
  const [render, setRender]                 = useState<boolean>(false);
  const [commentList, setCommentList]       = useState<Comment[]>();

  useEffect(() => {
    (async () => {
      setDetailLoading(true);
      setContentLoading(true);
      setCommentLoading(true);
      const article = await http.get<Article>(`v1/articles/${params.id}`);
      setArticleInfo(article);

      const comments           = await http.get<JavaCollectionModel<Comment>>(`v1/articles/${params.id}/comments`);
      const simplifiedComments = collectionModelSimplifier<Comment>(comments);

      const tableList = simplifiedComments?.list?.map((i) => ({
        ...i,
        status: i.status === 'NORMAL' ? (
          <Badge status="processing" color="green" text="正常" />
        ) : (
          <Badge status="default" color="gray" text="屏蔽" />
        ),
      }));
      setCommentList(tableList as any);

      setDetailLoading(false);
      setContentLoading(false);
      setCommentLoading(false);
    })();
  }, [render]);

  // 编辑文章资料
  const handleSubmitDetail = (data) => {
    setDetailLoading(true);
    setContentLoading(true);
    // eslint-disable-next-line no-param-reassign
    data.content = articleInfo?.content;

    http.put(`/v1/articles/${params.id}`, data)
      .then(() => {
        notification['success']({ message: '文章信息更新成功' });
        setEditDetail(false);
      })
      .catch(() => {
        notification['error']({ message: '文章信息更新失败' });
      })
      .finally(() => {
        setDetailLoading(false);
        setContentLoading(false);
        setRender(!render);
      });
  };

  // 编辑文章正文
  const handleSubmitContent = (data) => {
    setDetailLoading(true);
    setContentLoading(true);

    // eslint-disable-next-line no-param-reassign
    data.title       = articleInfo?.title;
    // eslint-disable-next-line no-param-reassign
    data.description = articleInfo?.description;
    // eslint-disable-next-line no-param-reassign
    data.cover       = articleInfo?.cover;
    // eslint-disable-next-line no-param-reassign
    data.status      = articleInfo?.status;

    http.put(`/v1/articles/${params.id}`, data)
      .then(() => {
        notification['success']({ message: '文章正文更新成功' });
        setEditContent(false);
      })
      .catch(() => {
        notification['error']({ message: '文章正文更新失败' });
      })
      .finally(() => {
        setDetailLoading(false);
        setContentLoading(false);
        setRender(!render);
      });
  };

  // 输入内容规范
  const validateMessages = {
    // eslint-disable-next-line no-template-curly-in-string
    required: '${label}不能为空！',
  };

  // 删除评论
  const deleteComment = (commentId: number) => {
    setCommentLoading(true);

    http.del(`v1/comments/${commentId}`)
      .then(() => {
        notification['success']({ message: '评论删除成功' });
      })
      .catch(() => {
        notification['error']({ message: '评论删除失败' });
      })
      .finally(() => {
        setCommentLoading(false);
        setRender(!render);
      });
  };

  // 评论列表属性
  const tableColumns = [
    {
      key: 'id',
      dataIndex: 'id',
      title: '评论ID',
    },
    {
      key: 'author',
      dataIndex: ['author', 'username'],
      title: '评论用户',
    },
    {
      key: 'status',
      dataIndex: 'status',
      title: '评论状态',
    },
    {
      key: 'content',
      dataIndex: 'content',
      title: '评论内容',
    },
    {
      key: 'publishDate',
      dataIndex: 'publishDate',
      title: '评论时间',
    },
    {
      key: 'likes',
      dataIndex: 'likes',
      title: '点赞数量',
    },
    {
      key: 'action',
      title: '执行操作',
      render: (comment) => (
        <Space>
          <Popconfirm
            title="确定删除该评论吗？删除后不可恢复！"
            okText="确定"
            cancelText="取消"
            onConfirm={() => {
              deleteComment(comment.id);
            }}
          >
            <Button type="text" danger>删除评论</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Block
        title="文章详细信息"
        loading={detailLoading}
        showBack
        description={(
          <Space>
            <Popconfirm
              title="确定删除该文章吗？删除之后不可恢复！"
              okText="确定"
              cancelText="取消"
              onConfirm={() => {
                deleteArticle(Number(params.id))
                  .then(() => {
                    notification['success']({ message: '文章删除成功' });
                    history.goBack();
                  }).catch(() => {
                    notification['error']({ message: '文章删除失败' });
                  });
              }}
            >
              <Button danger>删除文章</Button>
            </Popconfirm>
            <Button htmlType="submit" onClick={editDetail ? () => articleDetail.submit() : () => setEditDetail(true)}>
              {editDetail ? '保存' : '编辑'}
            </Button>
            {editDetail ? (
              <Button onClick={() => setEditDetail(false)}>取消</Button>
            ) : ''}
          </Space>
        )}
      >
        {
          !editDetail ? (
            <>
              <Row align="middle">
                <Col span={4}>
                  <Image
                    width={155}
                    alt="Article Cover"
                    src="http://pic.soutu123.cn/element_origin_min_pic/16/08/31/1457c67986055d6.jpg"
                  />
                </Col>
                <Col span={20}>
                  <Descriptions title={articleInfo?.title ?? '文章标题未定义'} bordered style={{ color: 'red' }}>
                    <DescriptionsItem label="文章ID">{articleInfo?.id ?? '未定义'}</DescriptionsItem>
                    <DescriptionsItem label="文章发表时间">{articleInfo?.publishDate ?? '未定义'}</DescriptionsItem>
                    <br />
                    <DescriptionsItem label="文章作者">{articleInfo?.author.username ?? '未定义'}</DescriptionsItem>
                    <DescriptionsItem label="最后一次更新时间">{articleInfo?.updateDate ?? '未定义'}</DescriptionsItem>
                    <br />
                    <Descriptions.Item label="文章状态" span={3}>
                      <Badge
                        status={articleInfo?.status === 'NORMAL' ? 'processing' : 'default'}
                        color={articleInfo?.status === 'NORMAL' ? 'green' : 'gray'}
                        text={articleInfo?.status === 'NORMAL' ? '正常' : '屏蔽'}
                      />
                    </Descriptions.Item>
                    <DescriptionsItem label="文章简介">{articleInfo?.description}</DescriptionsItem>
                  </Descriptions>
                </Col>
              </Row>
            </>
          ) : (
            <>
              <Row align="middle">
                <Col span={4}>
                  <Image
                    width={155}
                    alt="Article Cover"
                    src="http://pic.soutu123.cn/element_origin_min_pic/16/08/31/1457c67986055d6.jpg"
                  />
                </Col>
                <Col span={10}>
                  <Form
                    form={articleDetail}
                    name="newArticleDetail"
                    onFinish={handleSubmitDetail}
                    onFinishFailed={() => notification['error']({ message: '文章信息更新失败' })}
                    validateMessages={validateMessages}
                  >
                    <Form.Item
                      name="title"
                      label="文章标题"
                      rules={[{ required: true }]}
                      initialValue={articleInfo?.title}
                    >
                      <Input />
                    </Form.Item>
                    <Form.Item
                      name="cover"
                      label="文章封面"
                      rules={[{ required: true }]}
                      initialValue={articleInfo?.cover}
                    >
                      <Input />
                    </Form.Item>
                    <Form.Item
                      name="status"
                      label="文章状态"
                      initialValue={articleInfo?.status}
                    >
                      <Select>
                        <Option value="NORMAL">正常</Option>
                        <Option value="BLOCKED">屏蔽</Option>
                      </Select>
                    </Form.Item>
                    <Form.Item
                      name="description"
                      label="文章简介"
                      initialValue={articleInfo?.description}
                    >
                      <TextArea autoSize showCount />
                    </Form.Item>
                  </Form>
                </Col>
              </Row>
            </>
          )
        }
        <Divider orientation="left" style={{ fontSize: '90%' }}>文章数据一览</Divider>
        <Row justify="space-around">
          <Col span={4}>
            <Statistic title="阅读量" value={articleInfo?.views ?? '未定义'} prefix={<CloudOutlined />} />
          </Col>
          <Col span={4}>
            <Statistic title="点赞量" value={articleInfo?.likes ?? '未定义'} prefix={<LikeOutlined />} />
          </Col>
          <Col span={4}>
            <Statistic title="收藏量" value={articleInfo?.favorites ?? '未定义'} prefix={<StarOutlined />} />
          </Col>
        </Row>
        <Divider />
      </Block>

      <Block
        title="文章正文内容"
        loading={contentLoading}
        description={(
          <Space>
            <Button
              htmlType="submit"
              onClick={editContent ? () => articleContent.submit() : () => setEditContent(true)}
            >
              {editContent ? '保存' : '编辑'}
            </Button>
            {editContent ? (
              <Button onClick={() => setEditContent(false)}>取消</Button>
            ) : ''}
          </Space>
        )}
      >
        {
          !editContent ? (
            <DescriptionsItem>{articleInfo?.content ?? '未定义'}</DescriptionsItem>
          ) : (
            <Form
              form={articleContent}
              name="newArticleContent"
              onFinish={handleSubmitContent}
              onFinishFailed={() => notification['error']({ message: '文章正文更新失败' })}
              validateMessages={validateMessages}
            >
              <Form.Item
                name="content"
                rules={[{ required: true }]}
                initialValue={articleInfo?.content}
              >
                <TextArea autoSize={{ minRows: 3 }} showCount />
              </Form.Item>
            </Form>
          )
        }
        <Divider orientation="right" style={{ fontSize: '15px' }}>
          {`${articleInfo?.author.username} - ${articleInfo?.updateDate}`}
        </Divider>
      </Block>

      <Block title="文章评论" loading={commentLoading}>
        <Table
          rowKey="id"
          columns={tableColumns}
          dataSource={commentList}
        />
      </Block>
    </>
  );
};

export default ArticleInfo;
