//+-------------------------------------------------------------------------
//
//  评论相关类型定义
//
//  File:       comment.ts
//
//  Directory:  src/types
//
//  History:    9月-06-2021   QQK  Created
//
//--------------------------------------------------------------------------

import { User }    from 'types/user';
import { Article } from 'types/article';

export interface Comment {
  id: number,
  author: User,
  article: Article,
  status: string,
  content: string,
  publishDate: string,
  likes: number,
  parent: number
}
