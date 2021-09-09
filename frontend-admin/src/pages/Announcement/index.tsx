//+-------------------------------------------------------------------------
//
//  本站公告
//
//  File:       index.tsx
//
//  Directory:  src/pages/Index
//
//  History:    9月-08-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useEffect, useState } from 'react';
import Block                              from 'components/Block';
import { Article }                        from 'types/article';
import {
  Card, Col, Descriptions, Empty, Image, Modal, Row,
}                                         from 'antd';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useHistory }                     from 'react-router';
import Meta                               from 'antd/es/card/Meta';
import { getAnnouncementList }            from 'services/announcement';
import { useQuery }                       from 'react-query';
import { useDispatch, useSelector }       from 'react-redux';

const Announcement: FC = () => {
  const [announcementList, setAnnouncementList] = useState<Article[]>();
  const history                                 = useHistory();
  const [isModalVisible, setIsModalVisible]     = useState(false);
  const [currentArticle, setCurrentArticle]     = useState<Article>();

  const queryResult = useQuery(['announcementList'], getAnnouncementList);

  useEffect(() => {
    if (!queryResult.data) return;
    setAnnouncementList(queryResult.data);
  }, [queryResult.data]);

  return (
    <>
      <Block title="本站公告" loading={queryResult.isLoading}>
        {
          !announcementList ? (
            <Empty />
          ) : (
            <>
              <Modal
                title={currentArticle?.title}
                visible={isModalVisible}
                okText="查看详情"
                onOk={() => {
                  history.push(`/articles/${currentArticle.id}`);
                }}
                onCancel={() => setIsModalVisible(false)}
              >
                <Descriptions column={1}>
                  <Descriptions.Item>
                    {currentArticle?.description}
                  </Descriptions.Item>
                  <Descriptions.Item>
                    {currentArticle?.content}
                  </Descriptions.Item>
                </Descriptions>
              </Modal>
              <Row gutter={[32, 32]} style={{ marginTop: '16px', marginBottom: '16px' }}>
                {
                  announcementList?.map((i) => (
                    <Col span={6} key={i.id}>
                      <Card
                        hoverable
                        cover={(
                          <Image
                            preview={false}
                            alt="Announcement Cover"
                            src={i?.cover}
                            fallback="https://dummyimage.com/100x100"
                            height={320}
                            width="100%"
                          />
                        )}
                        onClick={() => {
                          setCurrentArticle(i);
                          setIsModalVisible(true);
                        }}
                      >
                        <Meta title={i.title} description={i.description} />
                      </Card>
                    </Col>
                  ))
                }
              </Row>
            </>
          )
        }
      </Block>
    </>
  );
};

export default Announcement;
