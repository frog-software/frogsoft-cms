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
      <Block title="关于我们">
        <iframe
          width="100%"
          height="800px"
          src="/Frogsoft-CMS.html"
          title="Traefik Dashboard"
          style={{ background: '#FFFFFF00' }}
        />
      </Block>
    </>
  );
};

export default About;
