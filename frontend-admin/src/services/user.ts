//+-------------------------------------------------------------------------
//
//  用户相关 service
//
//  File:       user.ts
//
//  Directory:  src/services
//
//  History:    Sep-02-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import { VITE_BACKEND_URL }                     from 'consts/url';
import http                                     from 'utils/http';
import { pagedModelSimplifier }                 from 'utils/common';
import { JavaPagedModel, SimplifiedPagedModel } from 'types/common';
import { User }                                 from 'types/user';
import { QueryFunctionContext }                 from 'react-query';

const USER_V1_URL = '/v1/users';

export const getUserList = (queryContext: QueryFunctionContext<any, any>) => {
  const [, page, size] = queryContext.queryKey;
  return new Promise<SimplifiedPagedModel<User>>((resolve, reject) => {
    http.get<JavaPagedModel<User>>(USER_V1_URL, { page, size })
      .then((res) => {
        const simplifiedModel = pagedModelSimplifier(res);
        simplifiedModel.list.forEach((item) => {
          item['authorities'] = item.roles.includes('ROLE_ADMIN')
            ? '管理员'
            : '用户';
        });
        resolve(simplifiedModel);
      })
      .catch(reject);
  });
};

export default {
  getUserList,
};
