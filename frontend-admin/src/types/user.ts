//+-------------------------------------------------------------------------
//
//  用户相关类型定义
//
//  File:       user.ts
//
//  Directory:  src/types
//
//  History:    Sep-02-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import { Article }    from 'types/article';
import { History }    from 'types/history';
import { Statistics } from 'types/statistics';

export interface User {
  email: string,
  username: string,
  avatar?: string
  roles: string[],
  favoriteArticles?: Article[],
  publishArticles?: Article[],
  comments?: Comment[],
  historyArticles?: History[],
  statistics?: Statistics
}
