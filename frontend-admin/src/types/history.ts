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

export interface History {
  id: number,
  time: string,
  userId: number,
  articleId: number
}
