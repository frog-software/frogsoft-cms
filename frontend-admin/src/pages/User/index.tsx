//+-------------------------------------------------------------------------
//
//  用户
//
//  File:       index.tsx
//
//  Directory:  src/pages/User
//
//  History:    Sep-03-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import React, { FC, Suspense } from 'react';
import Preview                 from 'components/Preview';
import { Route }               from 'react-router-dom';

const UserInfo = React.lazy(() => import('./UserInfo'));
const UserList = React.lazy(() => import('./UserList'));

const UserPage: FC = () => (
  <Suspense fallback={<Preview />}>
    <Route exact path="/users" component={UserList} />
    <Route exact path="/users/:username" component={UserInfo} />
  </Suspense>
);

export default UserPage;
