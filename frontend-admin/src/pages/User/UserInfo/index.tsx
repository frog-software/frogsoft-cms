//+-------------------------------------------------------------------------
//
//  用户详细信息
//
//  File:       index.tsx
//
//  Directory:  src/pages/User/UserInfo
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useEffect, useState }            from 'react';
import { User }                                      from 'types/user';
import { useParams }                                 from 'react-router-dom';
import Block                                         from 'components/Block';
import {
  Avatar,
  Badge,
  Button,
  Col,
  Descriptions,
  Form,
  Input,
  notification,
  Popconfirm,
  Row,
  Space,
  Switch,
  Table,
  Tabs,
}                                                    from 'antd';
import http                                          from 'utils/http';
import DescriptionsItem                              from 'antd/es/descriptions/Item';
import { useForm }                                   from 'antd/es/form/Form';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useHistory }                                from 'react-router';
import { Column }                                    from '@ant-design/charts';
import { CloudOutlined, FormOutlined, StarOutlined } from '@ant-design/icons';
import { Article }                                   from 'types/article';
import { deleteUser }                                from 'services/user';

const UserInfo: FC = () => {
  const params: { username: string }    = useParams();
  const [isLoading, setIsLoading]       = useState<boolean>(false);
  const [editable, setEditable]         = useState<boolean>(false);
  const [userInfo, setUserInfo]         = useState<User>();
  const [formDetail]                    = useForm();
  // const [formPassword]                  = useForm();
  const history                         = useHistory();
  const { TabPane }                       = Tabs;
  const [createdList, setCreatedList]   = useState<Article[]>();
  const [favoriteList, setFavoriteList] = useState<Article[]>();
  const [viewList, setViewList]         = useState<Article[]>();
  // const [editPassword, setEditPassword] = useState<boolean>(false);
  const [render, setRender]             = useState<boolean>(false);

  useEffect(() => {
    (async () => {
      const data = await http.get<User>(`v1/users/${params.username}`);

      // 用户创建的文章
      const tempCreatedList = data?.publishArticles?.map((i) => ({
        ...i,
        status: i.status === 'NORMAL' ? (
          <Badge status="processing" color="green" text="正常" />
        ) : (
          <Badge status="default" color="gray" text="屏蔽" />
        ),
      }));
      setCreatedList(tempCreatedList as any);

      // 用户收藏的文章
      const tempFavoriteList = data?.favoriteArticles?.map((i) => ({
        ...i,
        status: i.status === 'NORMAL' ? (
          <Badge status="processing" color="green" text="正常" />
        ) : (
          <Badge status="default" color="gray" text="屏蔽" />
        ),
      }));
      setFavoriteList(tempFavoriteList as any);

      // 用户浏览过的文章
      const tempViewList = data?.historyArticles?.map((i) => ({
        ...i,
        status: i.article.status === 'NORMAL' ? (
          <Badge status="processing" color="green" text="正常" />
        ) : (
          <Badge status="default" color="gray" text="屏蔽" />
        ),
      }));
      setViewList(tempViewList as any);

      setUserInfo(data);
    })();
  }, [render]);

  // 修改用户密码
  // const handleResetPassword = (data) => {
  //   setIsLoading(true);
  //
  //   http.put(`/v1/users/${params.username}`, data)
  //     .then(() => {
  //       message.success('密码重置成功！');
  //       setEditPassword(false);
  //     })
  //     .catch(() => {
  //       message.error('密码重置失败！');
  //     })
  //     .finally(() => {
  //       setIsLoading(false);
  //     });
  // };

  // 删除用户
  const handleDeleteUser = (username: string) => {
    deleteUser(username).then(() => {
      notification['success']({ message: '用户删除成功' });
      history.goBack();
    }).catch((error) => {
      notification['error']({ message: '用户删除失败', description: String(error) });
    });
  };

  // 编辑用户资料
  const handleEdit = (data) => {
    setIsLoading(true);

    if (data.roles) {
      // eslint-disable-next-line no-param-reassign
      data.roles = ['ROLE_USER', 'ROLE_ADMIN'];
    } else {
      // eslint-disable-next-line no-param-reassign
      data.roles = ['ROLE_USER'];
    }
    const tempData = {
      email: userInfo.email,
      username: data.username,
      roles: data.roles,
    };

    http.put(`/v1/users/${params.username}`, tempData)
      .then(() => {
        notification['success']({ message: '用户信息更新成功' });
        history.push(`/users/${tempData.username}`);
        setRender(!render);
        setEditable(false);
      })
      .catch((error) => {
        notification['error']({ message: '用户信息更新失败', description: String(error) });
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  // 输入内容规范
  const validateMessages = {
    // eslint-disable-next-line no-template-curly-in-string
    required: '${label}不能为空！',
    types: {
      // eslint-disable-next-line no-template-curly-in-string
      // email: '${label}不是一个合法的邮箱格式！',
    },
  };

  // 统计表格属性
  const statisticsTable = [
    {
      type: '文章发布数量',
      count: userInfo?.statistics?.publishArticlesNum,
    },
    {
      type: '文章总阅读量',
      count: userInfo?.statistics?.viewsNum,
    },
    {
      type: '文章总点赞量',
      count: userInfo?.statistics?.likesNum,
    },
    {
      type: '文章总收藏量',
      count: userInfo?.statistics?.favoritesNum,
    },
  ];

  // 统计表格配置
  const config = {
    data: statisticsTable,
    xField: 'type',
    yField: 'count',
    columnStyle: { fill: '#d6e4ff' },
    label: {

      position: 'middle',
      style: {
        fill: '#597ef7',
        opacity: 0.7,
      },
    },
    meta: {
      type: { alias: '类别' },
      count: { alias: '总量' },
    },
  };

  // 用户创建的文章、收藏的文章表格属性
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
          >
            查看详情
          </Button>
        </Space>
      ),
    },
  ];

  // 用户浏览记录表格属性
  const historyColumns = [
    {
      key: 'id',
      dataIndex: ['article', 'id'],
      title: '文章ID',
      sorter: {
        compare: (a, b) => a.id - b.id,
        multiple: 2,
      },
    },
    {
      key: 'title',
      dataIndex: ['article', 'title'],
      title: '标题',
    },
    {
      key: 'author',
      dataIndex: ['article', 'author', 'username'],
      title: '作者',
    },
    {
      key: 'publishDate',
      dataIndex: ['article', 'publishDate'],
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
          >
            查看详情
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Block
        loading={isLoading}
        title="用户详情"
        showBack
        description={(
          <Space>
            {/*<Button onClick={editPassword*/}
            {/*  ? () => formPassword.submit()*/}
            {/*  : () => setEditPassword(true)}*/}
            {/*>*/}
            {/*  {editPassword ? '保存密码' : '重置密码'}*/}
            {/*</Button>*/}
            <Popconfirm
              title="确定删除该用户吗？删除之后不可恢复！"
              okText="确定"
              cancelText="取消"
              onConfirm={() => {
                handleDeleteUser(params.username);
              }}
            >
              <Button danger type="primary">删除用户</Button>
            </Popconfirm>
            <Button
              htmlType="submit"
              type={editable ? 'primary' : 'ghost'}
              onClick={editable ? () => formDetail.submit() : () => setEditable(true)}
            >
              {editable ? '保存' : '编辑'}
            </Button>
            {editable ? (
              <Button type="ghost" onClick={() => { setEditable(false); }}>
                取消
              </Button>
            ) : ''}
          </Space>
        )}
      >
        <Row>
          <Col span={2}>
            <Avatar
              size={64}
              alt="User Avatar"
              shape="circle"
              src="http://pic.soutu123.cn/element_origin_min_pic/16/08/31/1457c67986055d6.jpg!/fw/700/quality/90/unsharp/true/compress/true"
            />
          </Col>
          <Col span={22}>
            {
              !editable ? (
                <Descriptions title={userInfo?.username ?? '用户名未定义'}>
                  <DescriptionsItem label="用户邮箱">{userInfo?.email ?? '未定义'}</DescriptionsItem>
                  <DescriptionsItem label="用户类型">
                    {userInfo?.roles.includes('ROLE_ADMIN') ? '管理员' : '普通用户'}
                  </DescriptionsItem>
                </Descriptions>
              ) : (
                <Col span={7}>
                  <Form
                    form={formDetail}
                    name="newUserInfo"
                    onFinish={handleEdit}
                    onFinishFailed={(error) => notification['error']({ message: '用户信息更新失败', description: String(error) })}
                    validateMessages={validateMessages}
                  >
                    <Form.Item
                      name="username"
                      label="用户名"
                      initialValue={userInfo?.username}
                      rules={[{ required: true }]}
                    >
                      <Input />
                    </Form.Item>
                    <Descriptions>
                      <DescriptionsItem label="用户邮箱">{userInfo?.email || '未定义'}</DescriptionsItem>
                    </Descriptions>
                    <Form.Item
                      name="roles"
                      label="用户类型"
                    >
                      <Switch
                        checkedChildren="管理员"
                        unCheckedChildren="普通用户"
                        defaultChecked={userInfo?.roles.includes('ROLE_ADMIN')}
                      />
                    </Form.Item>
                  </Form>
                </Col>
              )
            }
          </Col>
        </Row>
        {/*<Row>*/}
        {/*  <Col offset={2} span={5}>*/}
        {/*    {*/}
        {/*      !editPassword ? (*/}
        {/*        <></>*/}
        {/*      ) : (*/}
        {/*        <Form*/}
        {/*          form={formPassword}*/}
        {/*          name="newPassword"*/}
        {/*          onFinish={handleResetPassword}*/}
        {/*          validateMessages={validateMessages}*/}
        {/*        >*/}
        {/*          <Descriptions title="重置密码" />*/}
        {/*          <Form.Item name="oldPassword" label="输入旧密码" rules={[{ required: true }]}>*/}
        {/*            <Input.Password />*/}
        {/*          </Form.Item>*/}
        {/*          <Form.Item name="newPassword" label="输入新密码" rules={[{ required: true }]}>*/}
        {/*            <Input.Password />*/}
        {/*          </Form.Item>*/}
        {/*        </Form>*/}
        {/*      )*/}
        {/*    }*/}
        {/*  </Col>*/}
        {/*</Row>*/}
      </Block>
      <Block title="数据统计">
        <Row justify="center" gutter={120}>
          <Col span={24}>
            {/* eslint-disable-next-line react/jsx-props-no-spreading */}
            <Column {...config} />
          </Col>
        </Row>
      </Block>
      <Block>
        <Tabs defaultActiveKey="1">
          <TabPane
            tab={(
              <span>
                <FormOutlined />
                用户创建的文章
              </span>
            )}
            key="1"
          >
            <Table
              rowKey="id"
              loading={isLoading}
              columns={tableColumns}
              dataSource={createdList}
            />
          </TabPane>
          <TabPane
            tab={(
              <span>
                <StarOutlined />
                用户收藏的文章
              </span>
            )}
            key="2"
          >
            <Table
              rowKey="id"
              loading={isLoading}
              columns={tableColumns}
              dataSource={favoriteList}
            />
          </TabPane>
          <TabPane
            tab={(
              <span>
                <CloudOutlined />
                用户浏览的文章
              </span>
            )}
            key="3"
          >
            <Table
              rowKey="id"
              loading={isLoading}
              columns={historyColumns}
              dataSource={viewList}
            />
          </TabPane>
        </Tabs>
      </Block>
    </>
  );
};

export default UserInfo;
