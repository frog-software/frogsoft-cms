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

import React, { FC, useEffect, useState } from 'react';
import { User }                           from 'types/user';
import { useParams }                      from 'react-router-dom';
import Block                            from 'components/Block';
import {
  Avatar, Button, Col, Descriptions, Form, Popconfirm, Row, Space,
}                                       from 'antd';
import http                             from 'utils/http';
import DescriptionsItem                 from 'antd/es/descriptions/Item';
import { UserOutlined }                   from '@ant-design/icons';

const UserInfo: FC = () => {
  const params: { username: string } = useParams();
  const [isLoading, setIsLoading]    = useState<boolean>(false);
  const [editable, setEditable]      = useState<boolean>(false);
  const [userInfo, setUserInfo]      = useState<User>();

  useEffect(() => {
    (async () => {
      // const data = await http.get<User>(`/v1/users/${params.username}`);
      // console.log(params.username);
    })();
  }, []);

  // 修改用户密码
  const handleResetPassword = () => {
    console.log('点击了修改密码');
  };

  // 删除用户
  const handleDelete = () => {
    console.log('点击了删除用户');
  };

  // 编辑个人资料
  const handleSubmit = () => {
    console.log('点击了编辑');
  };

  return (
    <>
      <Block
        loading={isLoading}
        title="用户详情"
        showBack
        description={(
          <Space>
            <Button onClick={handleResetPassword}>重置密码</Button>
            <Popconfirm
              title="确定删除该用户吗？删除之后不可恢复"
              okText="确定"
              cancelText="取消"
              onConfirm={handleDelete}
            >
              <Button>删除用户</Button>
            </Popconfirm>
            <Button onClick={editable ? handleSubmit : () => setEditable(true)}>
              {editable ? '保存' : '编辑'}
            </Button>
          </Space>
        )}
      >
        {
          !editable ? (
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
                <Descriptions title={userInfo?.username || '用户名未定义'}>
                  <DescriptionsItem label="用户类型">{userInfo?.roles || '未定义'}</DescriptionsItem>
                  <DescriptionsItem label="邮箱">{userInfo?.email || '未定义'}</DescriptionsItem>
                </Descriptions>
              </Col>
            </Row>
          ) : (
            <Row>
              <Col span={2}>
                <Avatar size={64} alt="User Avatar" shape="circle" icon={<UserOutlined />} />
              </Col>
              <Col span={22}>
                <Form
                  validateMessages={{
                    required: "'${username}' 是必选字段",
                  }}
                />
              </Col>
            </Row>
          )
        }
      </Block>
    </>
  );
};

export default UserInfo;
