//+-------------------------------------------------------------------------
//
//  用户管理
//
//  File:       index.tsx
//
//  Directory:  src/pages/User
//
//  History:    Sep-02-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import React, { FC, useState } from 'react';
import Block                   from 'components/Block';
import { getUserList }         from 'services/user';
import { User }                from 'types/user';
import { useQuery }            from 'react-query';
import { message, Table }      from 'antd';

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

const UserManagement: FC = () => {
  const [currentPage, setCurrentPage]   = useState<number>(1);
  const [pageSize, setPageSize]         = useState<number>(10);
  const [previousData, setPreviousData] = useState<User[]>();
  const [previousTotal, setPreviousTotal] = useState<number>();

  const { isLoading, data } = useQuery(
    ['userList', currentPage - 1, pageSize],
    getUserList,
    {
      staleTime: 30000,
      onSuccess: (res) => {
        setPreviousData(res.list);
        setPreviousTotal(res.page.total);
      },
      onError: (err) => {
        message.error(String(err));
      },
    },
  );

  return (
    <>
      <Block title="用户管理">
        <Table
          rowKey="username"
          loading={isLoading}
          dataSource={data?.list || previousData}
          columns={tableColumns}
          pagination={{
            pageSize,
            total: data?.page?.total || previousTotal,
            showSizeChanger: true,
            onChange: (page, size) => {
              setPageSize(size);
              setCurrentPage(page);
            },
          }}
        />
      </Block>
    </>
  );
};

export default UserManagement;
