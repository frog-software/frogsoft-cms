# 后端贡献代码说明

## 代码规范

**必须遵守 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)**

- 对于 `IntelliJ IDEA`，你可以使用 [intellij-java-google-style.xml](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml) 来快速导入 `IntelliJ IDEA Code Style XML`

    > 导入方法：`Preferences` -> `Editor` -> `Code Style` -> `Java` -> ⚙ -> `Import Scheme` -> `IntelliJ IDEA Code Style XML`
    >
    > 导入后选择 `GoogleStyle` 即可，在格式化代码时 (Windows/Linux: `Ctrl` + `Alt` + `L`, macOS: `command` + `option` + `L` ) 将会按照所选的代码风格格式化代码。

- 对于 `Eclipse`，你可以使用 [eclipse-java-google-style.xml](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml) 来快速导入格式化配置文件

在 commit/push 之前请先格式化代码

## API 设计规范

**必须遵守 [Representational state transfer (REST)](https://en.wikipedia.org/wiki/Representational_state_transfer) 规范（特殊情况另行商议）**

> 参考资料：
>
> 1. [怎样用通俗的语言解释REST，以及RESTful？ - 知乎 (zhihu.com)](https://www.zhihu.com/question/28557115/answer/48094438)
> 2. [Getting Started | Building a RESTful Web Service (spring.io)](https://spring.io/guides/gs/rest-service/)
> 3. [Getting Started | Accessing Data with JPA (spring.io)](https://spring.io/guides/gs/accessing-data-jpa/)
> 4. **[Tutorial | Building REST services with Spring（建议阅读，有助于理解整个项目）](https://spring.io/guides/tutorials/rest/)**
> 5. [What is REST (restfulapi.net)](https://restfulapi.net/)

### 其他注意点

- 每个 API 需要有对应的 Apifox 文档和测试用例