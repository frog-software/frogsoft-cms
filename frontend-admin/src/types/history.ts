//+-------------------------------------------------------------------------
//
//  历史记录类型定义
//
//  File:       history.ts
//
//  Directory:  src/types
//
//  History:    9月-06-2021   QQK  Created
//
//--------------------------------------------------------------------------

import { User }    from 'types/user';
import { Article } from 'types/article';

export interface History {
  id: number,
  time: string,
  user: User,
  article: Article
}
