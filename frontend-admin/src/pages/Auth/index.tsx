//+-------------------------------------------------------------------------
//
//  权限认证
//
//  File:       index.tsx
//
//  Directory:  src/pages/Auth
//
//  History:    Sep-08-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------
import React, { FC, Suspense } from 'react';
import Preview                 from 'components/Preview';
import { Route }               from 'react-router-dom';

const Login = React.lazy(() => import('./Login'));

const UserPage: FC = () => (
  <Suspense fallback={<Preview />}>
    <Route exact path="/auth/login" component={Login} />
  </Suspense>
);

export default UserPage;
