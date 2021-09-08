//+-------------------------------------------------------------------------
//
//  用户列表
//
//  File:       index.tsx
//
//  Directory:  src/pages/User/UserManagement
//
//  History:    Sep-02-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import React, { FC, useEffect, useState } from 'react';
import Block                              from 'components/Block';
import { deleteUser, getUserList }        from 'services/user';
import { User }                           from 'types/user';
import { useQuery }                       from 'react-query';
import {
  Button, Col, notification, Popconfirm, Row, Space, Table,
}                                         from 'antd';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useHistory }                     from 'react-router';
import Search                             from 'antd/es/input/Search';

const UserList: FC = () => {
  const [currentPage, setCurrentPage]     = useState<number>(1);
  const [pageSize, setPageSize]           = useState<number>(10);
  const [userList, setUserList]           = useState<User[]>();
  const [totalItems, setTotalItems]       = useState<number>();
  const [deleteLoading, setDeleteLoading] = useState<boolean>(false);
  const history                           = useHistory();

  const {
    isLoading, data, refetch, remove,
  } = useQuery(
    ['userList', currentPage - 1, pageSize],
    getUserList,
    {
      staleTime: 5000,
      onError: (err) => {
        notification['error']({ message: String(err) });
      },
    },
  );

  const tableColumns = [
    {
      key: 'username',
      dataIndex: 'username',
      title: '用户名',
    },
    {
      key: 'email',
      dataIndex: 'email',
      title: '邮箱',
    },
    {
      key: 'authorities',
      dataIndex: 'authorities',
      title: '权限',
    },
    {
      key: 'action',
      title: '执行操作',
      render: (user) => (
        <Space>
          <Button
            onClick={() => {
              history.push(`/users/${user.username}`);
            }}
            type="text"
          >
            查看详情
          </Button>
          <Popconfirm
            title="确定删除该用户吗？删除之后不可恢复！"
            okText="确定"
            cancelText="取消"
            onConfirm={() => {
              setDeleteLoading(true);
              deleteUser(user.username)
                .then(() => {
                  notification['success']({ message: '用户删除成功' });
                  remove();
                  refetch({
                    throwOnError: true,
                    cancelRefetch: false,
                  });
                }).catch(() => {
                  notification['error']({ message: '用户删除失败' });
                }).finally(() => {
                  setDeleteLoading(false);
                });
            }}
          >
            <Button danger type="text">删除用户</Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  useEffect(() => {
    if (!data) return;

    setUserList(data.list);
    setTotalItems(data.page.total);
  }, [data]);

  return (
    <>
      <Block title="用户管理">
        <Row>
          <Col span={4} offset={20}>
            <Search
              placeholder="输入文章搜索关键字"
              onSearch={() => (console.log('搜索用户'))}
              enterButton
              style={{ marginBottom: '16px' }}
            />
          </Col>
          <Col span={24}>
            <Table
              rowKey="username"
              loading={isLoading || deleteLoading}
              dataSource={userList}
              columns={tableColumns}
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

export default UserList;
