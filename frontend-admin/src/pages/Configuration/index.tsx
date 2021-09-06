//+-------------------------------------------------------------------------
//
//  个性配置
//
//  File:       index.tsx
//
//  Directory:  src/pages/Configuration
//
//  History:    9月-06-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useState } from 'react';
import Block            from 'components/Block';

const Configuration: FC = () => {
  const [configuration, setConfiguration] = useState();

  return (
    <>
      <Block
        title="个性配置"
      >
        <h1>Hellow</h1>
      </Block>
    </>
  );
};

export default Configuration;
