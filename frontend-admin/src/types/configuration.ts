//+-------------------------------------------------------------------------
//
//  全局配置类型定义
//
//  File:       configuration.ts
//
//  Directory:  src/types
//
//  History:    9月-07-2021   QQK  Created
//
//--------------------------------------------------------------------------

export interface ConfigurationType {
  favicon: string,
  title: string,
  logo: string,
  email: Email,
  header: Header,
  footer: Footer,
}

export interface Email {
  password: string,
  account: string,
  body: string,
  title: string,
  host: string,
  port: string,
}

export interface Header {
  logo: string,
}

export interface Footer {
  logo: string,
}
