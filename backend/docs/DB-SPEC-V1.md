# DB-SPEC-V1

[toc]

## article

| Java 变量名 | Java 数据类型 | 数据库数据类型 | 是否空   | 类型    | 备注                      |
| ----------- | ------------- | -------------- | -------- | ------- | ------------------------- |
| id          | Long          | bigint         | not null | primary | increase auto             |
| author      | User          | bigint         | not null | foreign |                           |
| status      | Enum          | int            | not null |         | default 0 正常 0 已屏蔽 1 |
| publishDate | LocalDateTime | datetime       | not null |         |                           |
| updateDate  | LocalDateTime | datetime       | null     |         | 数据发生变动时更新        |
| views       | Integer       | int            | not null |         | default 0                 |
| favorites   | set<User>     |                |          |         | many to many[^1]          |
| likes       | set<User>     |                |          |         | many to many[^1]          |
| title       | String        | varchar(100)   | not null |         |                           |
| description | String        | varchar(255)   | null     |         |                           |
| content     | String        | longtext       | null     |         |                           |
| cover       | String        | varchar(255)   | not null |         | 封面图片 url              |

注：

- favorites 可能会有性能问题，之后可能需要添加 favorites_count

- likes 可能会有性能问题，之后可能需要添加 like_count

## comment

| Java 变量名 | Java 数据类型 | 数据库数据类型 |  是否空  |  类型   | 备注                      |
| ----------- | ------------- | -------------- | :------: | :-----: | ------------------------- |
| id          | Long          | bigint         | not null | primary | increase auto             |
| author      | User          | bigint         | not null | foreign | many to one               |
| article     | Article       | bigint         | not null | foreign | many to one               |
| status      | Enum          | int            | not null |         | default 0 正常 0 已屏蔽 1 |
| content     | String        | varchar(500)   | not null |         |                           |
| publishDate | LocalDateTime | datetime       | not null |         |                           |
| likes       | Integer       | int            | not null |         | 点赞数量 default 0        |
| parent      | Comment       | bigint         | nullable | foreign | 父评论的id                |

## user

| Java 变量名      | Java 数据类型 | 数据库数据类型 |  是否空  |  类型   | 备注             |
| ---------------- | ------------- | -------------- | :------: | :-----: | ---------------- |
| id               | Long          | bigint         | not null | primary |                  |
| username         | String        | varchar(255)   | not null |         | 用户名不能重复   |
| password         | String        | varchar(255)   | not null |         |                  |
| role             | Long          | bigint         | not null |         |                  |
| email            | String        | varchar(255)   | not null |         |                  |
| favoriteArticles | set<Article>  |                |          |         | many to many[^1] |
| likeArticles     | set<Article>  |                |          |         | many to many[^1] |

## history

| Java 变量名 | Java 数据类型 | 数据库数据类型 |  是否空  |  类型   | 备注                           |
| ----------- | ------------- | -------------- | :------: | :-----: | ------------------------------ |
| id          | Long          | bigint         | not null | primary |                                |
| time        | LocalDateTime | datetime       | not null |         |                                |
| user        | User          | bigint         | not null | foreign | 用户若删除，则删除历史记录     |
| article     | Article       | bigint         | not null | foreign | 文章若删除，则指向“文章已删除” |

## config

| Java 变量名 | Java 数据类型 | 数据库数据类型 |  是否空  | 类型 | 备注         |
| ----------- | ------------ | -------------- | :------: | :---: | ------------ |
| favicon     | String       | varchar(255)   | not null |      | 网站图标 url |
| title       | String       | varchar(255)   | not null |      | 网站名       |
| logoHeader | String       | varchar(255)   | not null |      | 页眉 logo    |
| logoFooter | String       | varchar(255)   | not null |      | 页脚 logo    |

## other

> 用来存储一些乱七八糟的东西

| Java 变量名 | Java 数据类型 | 数据库数据类型 |  是否空  | 类型 | 备注 |
| ------ | ------------ | -------------- | :------: | :---: | ---- |
| key    | String       | varchar(255)   | not null |      |      |
| value  | String       | varchar(255)   | not null |      |      |

## 参考资料

- [^1]: Many to many的用法可以参见文章：[Many-To-Many Relationship in JPA | Baeldung](https://www.baeldung.com/jpa-many-to-many)

