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

import React, { FC, useEffect, useState }                        from 'react';
import { Article }                                               from 'types/article';
import Title                                                     from 'antd/es/typography/Title';
import Block                                                     from 'components/Block';
import { useParams }                                             from 'react-router-dom';
import http                                                      from 'utils/http';
import {
  Avatar, Button, Col, Descriptions, Divider, Image, Row, Space, Statistic,
} from 'antd';
import DescriptionsItem                                          from 'antd/es/descriptions/Item';
import {
  BookOutlined, CloudOutlined, LikeOutlined, StarOutlined,
} from '@ant-design/icons';

const ArticleInfo: FC = () => {
  const params: { id: string }       = useParams();
  const [articleInfo, setAricleInfo] = useState<Article>();

  useEffect(() => {
    (async () => {
      const data = await http.get<Article>(`http://127.0.0.1:4523/mock/419258/v1/articles/${params.id}`);
      setAricleInfo(data);
      // console.log('这是假数据', data);
    })();
  }, []);

  return (
    <>
      <Block
        title="文章详细信息"
        showBack
        description={(
          <Space>
            <Button>编辑</Button>
          </Space>
        )}
      >
        <Row>
          <Col span={4}>
            <Image width={155} alt="Article Cover" src="http://pic.soutu123.cn/element_origin_min_pic/16/08/31/1457c67986055d6.jpg" />
          </Col>
          <Col span={20}>
            <Descriptions title={articleInfo?.title || '文章标题未定义'} bordered style={{ color: 'red' }}>
              <DescriptionsItem label="文章ID">{articleInfo?.id || '未定义'}</DescriptionsItem>
              <DescriptionsItem label="文章发表时间">{articleInfo?.publishDate || '未定义'}</DescriptionsItem>
              <br />
              <DescriptionsItem label="文章作者">{articleInfo?.author.username || '未定义'}</DescriptionsItem>
              <DescriptionsItem label="最后一次更新时间">{articleInfo?.updateDate || '未定义'}</DescriptionsItem>
            </Descriptions>
          </Col>
        </Row>
        <Divider />
      </Block>
    </>
  );
};

export default ArticleInfo;
