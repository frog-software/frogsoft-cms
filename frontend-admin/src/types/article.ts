//+-------------------------------------------------------------------------
//
//  文章相关类型定义
//
//  File:       article.ts
//
//  Directory:  src/types
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import { User } from 'types/user';

export interface Article {
  id: number,
  author: User,
  status: string,
  publishDate: string,
  updateDate: string,
  views: number,
  likes: number,
  favorites: number,
  title: string,
  description: string,
  cover: string,
  content: string,
}
