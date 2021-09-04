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

import React, {
  FC, useCallback, useEffect, useState,
}                       from 'react';
import { User }         from 'types/user';
import { useParams }    from 'react-router-dom';
import Block            from 'components/Block';
import {
  Avatar, Button, Col, Descriptions, Form,
  FormInstance, Input, message, Popconfirm, Row, Select, Space, Statistic, Table,
} from 'antd';
import http             from 'utils/http';
import DescriptionsItem                                                        from 'antd/es/descriptions/Item';
import {
  BookOutlined, CloudOutlined, LikeOutlined, StarOutlined, UserOutlined,
} from '@ant-design/icons';
import { useForm }                                                             from 'antd/es/form/Form';

const UserInfo: FC = () => {
  const params: { username: string } = useParams();
  const [isLoading, setIsLoading]    = useState<boolean>(false);
  const [editable, setEditable]      = useState<boolean>(false);
  const [userInfo, setUserInfo]      = useState<User>();
  const [form] = useForm();
  const { Option } = Select;

  const EditableContext = React.createContext<FormInstance<any> | null>(null);

  useEffect(() => {
    (async () => {
      const data = await http.get<User>('http://127.0.0.1:4523/mock/419258/v1/users/1');
      // console.log(params.username);
      setUserInfo(data);
      console.log('这是假数据', data);
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
  const handleSubmit = (fata) => {
    // setIsLoading(true);
    console.log('nbnb', fata);
    //  给后端传回数据，用一个Promise写法

    //  传成功了就：
    // message.success('用户信息更新成功！');
    // setIsLoading(false);

    //  传失败了就：
    //   message.error('用户信息更新失败！', error);
    setEditable(false);
  };

  const validateMessages = {
    required: '${label}不能为空！',
    types: {
      email: '${label}不是一个合法的邮箱格式！',
      username: '${label}已存在！',
    },
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
            <Button htmlType="submit" onClick={editable ? () => form.submit() : () => setEditable(true)}>
              {editable ? '保存' : '编辑'}
            </Button>
            {editable ? (
              <Button onClick={() => setEditable(false)}>取消</Button>
            ) : ''}
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
                <Avatar
                  size={64}
                  alt="User Avatar"
                  shape="circle"
                  src="http://pic.soutu123.cn/element_origin_min_pic/16/08/31/1457c67986055d6.jpg!/fw/700/quality/90/unsharp/true/compress/true"
                />
              </Col>
              <Col span={7}>
                <Form
                  form={form}
                  name="newUserInfo"
                  onFinish={handleSubmit}
                  onFinishFailed={() => message.error('用户信息更新失败')}
                  validateMessages={validateMessages}
                >
                  <Form.Item
                    name="username"
                    label="用户名"
                    rules={[{ required: true }]}
                    initialValue={userInfo.username}
                  >
                    <Input allowClear />
                  </Form.Item>
                  <Form.Item
                    name="email"
                    label="用户邮箱"
                    rules={[{ type: 'email', required: true }]}
                    initialValue={userInfo.email}
                  >
                    <Input allowClear />
                  </Form.Item>
                  <Form.Item
                    name="roles"
                    label="用户权限"
                    initialValue={userInfo.roles}
                  >
                    <Select defaultValue="普通用户">
                      <Option value="normal">普通用户</Option>
                      <Option value="admin">管理员</Option>
                    </Select>
                  </Form.Item>
                </Form>
              </Col>
            </Row>
          )
        }
      </Block>
      <Block title="数据统计">
        <Row justify="center" space-around gutter={120}>
          <Col span={5}>
            <Statistic title="文章发布数量" value="接口都没写" prefix={<BookOutlined />} />
          </Col>
          <Col span={5}>
            <Statistic title="文章总浏览量" value="接口都没写" prefix={<CloudOutlined />} />
          </Col>
          <Col span={5}>
            <Statistic title="文章总点赞量" value="接口都没写" prefix={<LikeOutlined />} />
          </Col>
          <Col span={5}>
            <Statistic title="文章总收藏量" value="接口都没写" prefix={<StarOutlined />} />
          </Col>
        </Row>
      </Block>
      <Block />
    </>
  );
};

export default UserInfo;
