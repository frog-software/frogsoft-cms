# **ADMIN-SPEC-V1**



[toc]



## US 用户管理 *Manage User* [^①]

### US01 用户列表 `/users`

### US02 编辑资料 `/users/:username`

### US03 重置密码 `/users/:username`

### US04 删除用户 `/users/:username`

### US05 更改权限 `/users/:username`  *(未来拓展)*



## AT 文章管理 *Manage Article* [^②]

### AT01 文章列表 `/articles`

### AT02 编辑文章 `/articles/:id`

### AT03 删除文章 `/articles/:id`



## CM 评论管理 *Manage Command* [^③]

### CM01 删除评论 `/articles/:id`



## AB 关于我们 *About Us* [^④]

### AB01 介绍我们 `/about`



[^①]: 查看用户，可以对用户的资料进行更改
[^②]: 查看文章，可以对文章的信息进行编辑
[^③]: 查看评论，可以对评论进行删除操作
[^④]: 介绍关于此项目团队的信息