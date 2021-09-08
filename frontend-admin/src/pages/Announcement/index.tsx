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

import React, { FC, useEffect, useState }                 from 'react';
import Block                                              from 'components/Block';
import http                                               from 'utils/http';
import { Article }                                        from 'types/article';
import { JavaCollectionModel, SimplifiedCollectionModel } from 'types/common';
import { collectionModelSimplifier }                      from 'utils/common';
import {
  Card, Col, Descriptions, Image, Modal, Row, Empty, notification,
}                                                         from 'antd';
import { useHistory }                                     from 'react-router';
import Meta                                               from 'antd/es/card/Meta';

const Announcement: FC = () => {
  const [announcementList, setAnnouncementList] = useState<Article[]>();
  const [loading, setLoading]                   = useState<boolean>(false);
  const history                                 = useHistory();
  const [isModalVisible, setIsModalVisible]     = useState(false);
  const [currentArticle, setCurrentArticle]     = useState<Article>();

  useEffect(() => {
    setLoading(true);

    http.get<JavaCollectionModel<Article>>('/v1/home/announcements')
      .then((data) => {
        const simplifiedData = collectionModelSimplifier(data);
        setAnnouncementList(simplifiedData.list);
        notification['success']({ message: '获取公告成功' });
      })
      .catch((error) => {
        notification['error']({ message: '获取公告失败', description: String(error) });
      })
      .finally(() => {
        setLoading(false);
      });
    // const data = await http.get<JavaCollectionModel<Article>>('http://127.0.0.1:4523/mock/419258/v1/home/announcements');
  }, []);

  return (
    <>
      <Block title="本站公告" loading={loading}>
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
