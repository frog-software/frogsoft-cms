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
  Badge, Comment, Avatar,
  Button, Col, Descriptions, Divider, Image, Row, Space, Statistic, Popconfirm, Form, Select, message, Input,
}                        from 'antd';
import DescriptionsItem  from 'antd/es/descriptions/Item';
import {
  CloudOutlined, LikeOutlined, StarOutlined,
}                        from '@ant-design/icons';
import { useForm }       from 'antd/es/form/Form';
import { useHistory }    from 'react-router';
import TextArea          from 'antd/es/input/TextArea';
import { deleteArticle } from 'services/article';

const ArticleInfo: FC = () => {
  const params: { id: string }       = useParams();
  const [articleInfo, setAricleInfo] = useState<Article>();
  const [editDetail, setEditDetail]      = useState<boolean>(false);
  const [editContent, setEditContent]      = useState<boolean>(false);
  const [articleDetail]              = useForm();
  const [articleContent]             = useForm();
  const history                      = useHistory();
  const { Option }                     = Select;

  useEffect(() => {
    (async () => {
      const data = await http.get<Article>(`v1/articles/${params.id}`);
      setAricleInfo(data);
      // console.log('这是假数据', data);
    })();
  }, []);

  // 编辑文章资料
  const handleSubmitDetail = (data) => {
    console.log(data);
    setEditDetail(false);
  };

  // 编辑文章正文
  const handleSubmitContent = (data) => {
    console.log(data);
    setEditContent(false);
  };

  // 删除文章
  const handleDelete = async (articleId) => {
    await deleteArticle(articleId);
    history.goBack();
  };

  // 输入内容规范
  const validateMessages = {
    required: '${label}不能为空！',
  };

  return (
    <>
      <Block
        title="文章详细信息"
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
                    message.success('文章删除成功');
                    history.goBack();
                  }).catch(() => {
                    message.error('文章删除失败');
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
                  <Descriptions title={articleInfo?.title || '文章标题未定义'} bordered style={{ color: 'red' }}>
                    <DescriptionsItem label="文章ID">{articleInfo?.id || '未定义'}</DescriptionsItem>
                    <DescriptionsItem label="文章发表时间">{articleInfo?.publishDate || '未定义'}</DescriptionsItem>
                    <br />
                    <DescriptionsItem label="文章作者">{articleInfo?.author.username || '未定义'}</DescriptionsItem>
                    <DescriptionsItem label="最后一次更新时间">{articleInfo?.updateDate || '未定义'}</DescriptionsItem>
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
                    onFinishFailed={() => message.error('文章信息更新失败')}
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
            <Statistic title="阅读量" value={articleInfo?.views || '未定义'} prefix={<CloudOutlined />} />
          </Col>
          <Col span={4}>
            <Statistic title="点赞量" value={articleInfo?.likes || '未定义'} prefix={<LikeOutlined />} />
          </Col>
          <Col span={4}>
            <Statistic title="收藏量" value={articleInfo?.favorites || '未定义'} prefix={<StarOutlined />} />
          </Col>
        </Row>
        <Divider />
      </Block>

      <Block
        title="文章正文内容"
        description={(
          <Space>
            <Button htmlType="submit" onClick={editContent ? () => articleContent.submit() : () => setEditContent(true)}>
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
            <DescriptionsItem>{articleInfo?.content || '未定义'}</DescriptionsItem>
          ) : (
            <Form
              form={articleContent}
              name="newArticleContent"
              onFinish={handleSubmitContent}
              onFinishFailed={() => message.error('文章正文更新失败')}
              validateMessages={validateMessages}
            >
              <Form.Item
                name="content"
                rules={[{ required: true }]}
                initialValue={articleInfo?.content}
              >
                <TextArea autoSize showCount />
              </Form.Item>
            </Form>
          )
        }
        <Divider orientation="right" style={{ fontSize: '15px' }}>
          {`${articleInfo?.author.username} - ${articleInfo?.updateDate}`}
        </Divider>
      </Block>

      <Block title="文章评论">
        <Comment
          author={<p>Han Solo</p>}
          avatar={(
            <Avatar
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
              alt="Han Solo"
            />
          )}
          content={(
            <p>
              We supply a series of design principles, practical patterns and high quality design
              resources (Sketch and Axure), to help people create their product prototypes beautifully
              and efficiently.
            </p>
          )}
          datetime={(
            <p>几秒前</p>
          )}
        />
      </Block>
    </>
  );
};

export default ArticleInfo;
