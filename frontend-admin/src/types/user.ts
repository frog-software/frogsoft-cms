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

export interface User {
  email: string,
  username: string,
  roles: string[],
  favouriteArticles?: number[],
  comments?: number[],
  histories?: number[]
}
