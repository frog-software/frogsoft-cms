# **ADMIN-SPEC-V1**

[toc]



## US 用户管理 *Manage User* 

### US01 用户列表 `/users`

- 以列表的形式展示用户数据

#### US0101 提供更多用户排序

- 可以通过用户名、权限等属性进行排序

### US02 编辑资料 `/users/:username`

- 可以编辑用户的详细资料

### US03 重置密码 `/users/:username`

- 可以直接重置该账户的登录密码

### US04 删除用户 `/users/:username`

- 可以直接删除用户的所有账户数据

### US05 更改权限 `/users/:username`

- 可以更改某个账户的权限为管理员或普通用户



## AT 文章管理 *Manage Article*

### AT01 文章列表 `/articles`

- 以列表的形式展示文章数据

#### AT0101 提供更多文章排序

- 可以通过文章的点赞量、收藏量、浏览量或发布时间进行排序

#### AT0102 搜索文章

- 可以根据关键字搜索指定文章

### AT02 编辑文章 `/articles/:id`

- 可以编辑文章的详细信息

### AT03 删除文章 `/articles/:id`

- 可以删除文章的所有数据



## CM 评论管理 *Manage Command*

### CM01 删除评论 `/articles/:id`

- 可以删除文章的某条评论



## WA 本站公告 *Web Announcement*
### WA01 修改公告 `/announcement`
- 可以修改本站的公告



## GS 全局配置 *Global Configuration*

### GS01 站点属性配置 `/configuration`

- 设置网站的站点名称、站点图标和LOGO

### GS02 验证邮箱配置 `/configuration`

- 设置邮箱账号、密码、服务器主机、服务器端口、邮件标题和正文

### GS03 页面属性配置 `/configuration`

- 设置页头和页脚的LOGO

#### GS0301 页面配置预览
- 可以预览页面配置的样式




## AB 关于我们 *About Us*

### AB01 介绍我们 `/about`

- 介绍关于此项目团队的信息