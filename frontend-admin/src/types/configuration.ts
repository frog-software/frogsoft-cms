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
  emailDto: EmailDto,
  headerDto: HeaderDto,
  footerDto: FooterDto,
}

export interface EmailDto {
  password: string,
  account: string,
  body: string,
  title: string,
  host: string,
  port: string,
}

export interface HeaderDto {
  logo: string,
}

export interface FooterDto {
  logo: string,
}
