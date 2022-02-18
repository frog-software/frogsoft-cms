# Frogsoft CMS



Frogsoft CMS - Universal SaaS Content Management System

> 文档和代码正在完善中......
>
> 未校对的开发规范文档、项目报告和测试报告等在 `docs` 目录下可供查阅（包括 `./docs/`、`./backend/docs`、`./frontend-admin/docs`、`./frontend-user/docs`）

## 启动项目

1. 安装 `Docker` 和 `docker-compose`，确保良好的互联网访问
2. 你可能需要检查环境变量中的设置（例如端口配置等），只需将对应的环境变量复制为 `.env.<env_name>.local` 修改其中的内容即可，避免直接修改仓库中的配置，以防泄露配置。例如复制 `.env.prod` 为 `.env.prod.local` ，`.env.prod.local` 中的修改将会拥有更高优先级并生效。
3. 运行 `./runner.sh start deploy` 即可，在 `user.localhost` 和 `admin.localhost` 测试用户端和管理端（可选：使用 `-d` 参数后台运行，`-v` 显示 debug 消息）