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
import { getUserList }                    from 'services/user';
import { User }                           from 'types/user';
import { useQuery }                       from 'react-query';
import { message, Table }                 from 'antd';

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
];

const UserList: FC = () => {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [pageSize, setPageSize]       = useState<number>(10);
  const [userList, setUserList]       = useState<User[]>();
  const [totalItems, setTotalItems]   = useState<number>();

  const { isLoading, data } = useQuery(
    ['userList', currentPage - 1, pageSize],
    getUserList,
    {
      staleTime: 30000,
      onError: (err) => {
        message.error(String(err));
      },
    },
  );

  useEffect(() => {
    if (!data) return;

    setUserList(data.list);
    setTotalItems(data.page.total);
  }, [data]);

  return (
    <>
      <Block title="用户管理">
        <Table
          rowKey="username"
          loading={isLoading}
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
      </Block>
    </>
  );
};

export default UserList;
