//+-------------------------------------------------------------------------
//
//  关于我们
//
//  File:       index.tsx
//
//  Directory:  src/pages/About
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useState } from 'react';
import Block                   from 'components/Block';

const About: FC = () => {
  const [about, setAbout] = useState();

  return (
    <>
      <Block title="关于我们" />
    </>
  );
};

export default About;
