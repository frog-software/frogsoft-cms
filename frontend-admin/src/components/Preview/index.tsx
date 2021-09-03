import React, { FC } from 'react';
import { Skeleton, Typography } from 'antd';

const Preview: FC = () => (
  <div
    style={{
      backgroundColor: '#fff',
      margin: 24,
      padding: 24,
      minHeight: '85vh',
      borderRadius: 10,
    }}
  >
    <Skeleton active />
    <Typography.Title style={{ color: '#999', textAlign: 'center', margin: '60px 0px' }}>
      加载模块中...
    </Typography.Title>
    <Skeleton active />
  </div>
);

export default Preview;
