# **USER-SPEC-V1**

[toc]



## AU 权限模块

### AU01 用户登录 `/login`

#### AU0101 密码登录

- 允许用户通过账户密码登录

#### AU0102 邮箱登录（未来拓展）

- 允许用户通过邮箱登录

#### AU0103 验证码（未来拓展）

- 用户可以通过验证码认证

### AU02 用户注册 `/register`

- 注册需要用户输入用户名、密码、重复密码、邮箱以及认证码

### AU03 忘记密码 `/forgetpwd`

- 用户通过输入用户名或邮箱获得邮箱验证码，从而重置密码



## AT 文章模块 || 卡片

### AT01 文章列表 `/articles`

#### AT0101 支持更多文章排序

- 文章可以根据更新时间、浏览量和收藏数排序

### AT02 查看文章 `/articles/:id`

- 能够查看封面、标题、简介、正文（Markdown）

### AT03 文章编辑 `/articles/edit/:id`

- 可以创建文章以及编辑文章的详细信息

### AT04 文章操作 `/articles/:id`

#### AT0401 删除文章

- 能够删除整篇文章所有信息

#### AT0402 点赞文章

- 能够点赞文章，以及取消点赞

#### AT0403 收藏文章

- 能够收藏文章，以及取消收藏

#### AT0404 评论文章

##### AT040401 创建评论

- 对文章创建一条评论

##### AT040402 回复评论

- 能够对文章已存在的评论进行评论

##### AT040403 删除回复

- 删除对评论的评论

##### AT040404 点赞评论

- 能够点赞评论，以及取消点赞



## HM 首页模块

### HM01 推荐阅读 `/home`

- 在首页创建一个能够为用户推荐文章的轮播图

### HM02 热门文章 `/home`

- 以列表形式推荐的文章（与推荐阅读不同）

### HM03 搜索文章 `/search`

- 打错字的修正（未来拓展）
- 近义词搜索（未来拓展）

### HM04 本站公告 `/home`

- 展示本站的公告信息

### HM05 浏览历史 `/history`

- 能够查看用户观看文章的浏览历史

### HM06 账户登出 `/home`

- 可以退出账户登录状态



## GL 网页全局

### GL01 标签页

GL0101 标题
GL0102 图标

### GL02 页首

GL0201 Logo
GL0202 菜单
GL0203 搜索

### GL03 页脚

GL0301 Logo
GL0302 推荐链接
GL0303 网页
