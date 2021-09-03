//+-------------------------------------------------------------------------
//
//  用户信息
//
//  File:       index.tsx
//
//  Directory:  src/pages/User/UserInfo
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useState } from 'react';
import { User }                from 'types/user';
import { useParams } from 'react-router-dom';
import Block                   from 'components/Block';

const UserInfo: FC = () => {
  const params: { id: string } = useParams();
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [ediable, setEdiable]     = useState<boolean>(false);
  const [userInfo, setUserInfo]   = useState<User>();

  return (
    <>
      <Block
        loading={isLoading}
        title="用户详情"
        showBack

      />
    </>
  );
};

export default UserInfo;
