

# 后端贡献代码说明

[toc]

## 代码规范

**必须遵守 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)**

- 对于 `IntelliJ IDEA`，你可以使用 [intellij-java-google-style.xml](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml) 来快速导入 `IntelliJ IDEA Code Style XML`

    > 导入方法：`Settings ` / `Preferences` -> `Editor` -> `Code Style` -> `Java` -> ⚙ -> `Import Scheme` -> `IntelliJ IDEA Code Style XML`
    >
    > 导入后选择 `GoogleStyle` 即可，在格式化代码时 (Windows/Linux: `Ctrl` + `Alt` + `L`, macOS: `command` + `option` + `L` ) 将会按照所选的代码风格格式化代码。

- 对于 `Eclipse`，你可以使用 [eclipse-java-google-style.xml](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml) 来快速导入格式化配置文件

在 commit/push 之前请先格式化代码

## API 设计规范

**必须遵守 [Representational state transfer (REST)](https://en.wikipedia.org/wiki/Representational_state_transfer) 规范（特殊情况另行商议）**

> 参考资料：
>
> 1. [怎样用通俗的语言解释REST，以及RESTful？ - 知乎 (zhihu.com)](https://www.zhihu.com/question/28557115/answer/48094438)
> 2. [Getting Started | Building a RESTful Web Service (spring.io) REST 样例](https://spring.io/guides/gs/rest-service/)
> 4. **[Tutorial | Building REST services with Spring（有助于实战编写符合 REST 的项目，但是较长，建议放最后看）](https://spring.io/guides/tutorials/rest/)**
> 5. [What is REST (restfulapi.net)](https://restfulapi.net/)

### 已设计 API 版本

> 本地访问时可能需要 按住 `Ctrl` 再点击 才能跳转

-  [V1](API-SPEC-V1.md) ：创建日期 20210831

## Apifox 文档规范

### 新建接口

从 API 文档中找到自己要开发的功能，查看相关信息，新建 Apifox 接口。

#### 基础信息

- 接口类型：同文档中规定的类型，常用`GET` `POST` `PUT` `DELETE` 等
- 接口路径：`/版本号/功能路径` 从文档中获取信息，例如 `v1/auth/login`
- 名称：`功能编号 功能要点` ，例如 `AU01 登录`
- 分类：`/版本/功能大点`，例如 `/v1/AU 权限`
- 状态：默认为 `开发中`
- 责任人：默认为 自己（开发这个端口的人）
- 标签：按实际权限填写，常用 `all` `authenticated` `user` `admin` `me`等，具体用法见后文
- 说明：有需要额外说明的注意事项

#### 关于 `标签` 

| 标签            | 解释             |
| --------------- | ---------------- |
| `all`           | 所有人均可以访问 |
| `authenticated` | 需要权限验证     |
| `user`          | 普通用户有全权限 |
| `admin`         | 管理员用户有权限 |
| `me`            | 个人用户有权限   |

- 需要权限认证的接口都应该加上 `authenticated` 标签
- 原则上 `user` 有权限的接口，`admin` 也有权限
- 举例来说 `US0101 获取用户列表` 接口，需要管理员权限那么他的标签应该为`authenticated` 和`admin` 权限

#### 请求参数

- `Query参数`：一般用于 `GET` 和 `DELETE` 请求
- `Body参数`：一般用于 `POST` 和 `PUT` 请求
  - 格式选择 `json`
  - 数据结构：可以使用 `JSON 智能识别 ` 功能帮助创建
  - 数据结构：常用的数据类型可以存储为 `数据模型`
  - 示例值：挑选一组经典的数据作为示例

#### 返回Response

- 根据实际情况启用 `全局 Response`
- 根据实际情况增加类型
- 自定义的返回必须创建`返回 Response 示例`，全局 Response 按需创建

###  测试

#### 测试环境

由于接口访问大多需要 `Header - Authorization`， 若一个个添加未免过于麻烦，我们可以使用 Apifox 提供的测试环境功能。

1. 在 窗口右上方 点击 按钮 进入 `全局参数` 的管理界面
2. 在 `Header` 中添加 `Authorization` 参数
3. 填写默认的 `token` 值，推荐使用动态的 `Bearer {{token}}`，其中 `token` 为全局变量
4. 全局变量 `token` 的数值可以通过后置脚本自动更新，具体介绍见 `脚本`

#### 测试数据

点击 `运行` 界面的 `🔄自动生成` ，会根据配置的 `Mock`  随机生成数据

#### 测试用例

对于常用的、经典的数据，应当点击 `保存为用例`，并命名为 `状态码-情况` ，方便之后调用

例如对于 `AU01 登录接口`，登录管理员是一个常用的用例，则其可以保存为 `201-登录管理员`

#### 脚本

>  [脚本介绍 | Apifox 使用文档](https://www.apifox.cn/help/app/scripts/)

我们可以通过脚本进行各种自动操作

例如我们想在登录后自动更新  `token`，这属于 `后置操作` 也就是返回结果后进行的操作，可以这样操作：

![更新token的脚本只](images/script-token.png)

### 其他注意点

- 每个 API 需要有对应的 Apifox 文档和测试用例

## 常用代码速览

> 最好是自己阅读源代码，注释正在添加中……

### exception

异常的使用并不复杂：照常使用 `controller` 函数，当解析请求遇到不符合预期的情况时，自动或手动抛出相应的异常 。

通过已经封装过的这些异常类，你只需要指定抛出异常返回的信息，而无需手动写各类返回。

假设我们有一个 `Foo` 函数，假设只有用户自己和管理员才能进行操作，那么我们可以这样操作：

```java
  @GetMapping("/{username}")
  public ResponseEntity<EntityModel<返回类型>> Foo(
      @PathVariable(value = "username") String username, // 请求参数
      @AuthenticationPrincipal User authenticatedUser // JWT鉴权对应的用户信息
  ) {

   // 其他操作
      
    if (!username.equals(authenticatedUser.getUsername())
        && !authenticatedUser.getRoles().contains("ROLE_ADMIN")
    ) {
      throw new ForbiddenException("你只能查看自己的信息");
    }

    // 其他操作

    return ResponseEntity.ok().body(一个EntityModel<返回类型>的对象);
  }
```

#### basic

这里存放了已经封装的几种基础错误：

| 异常类型     | HTTP状态码 | 说明                                   |
| ------------ | ---------- | -------------------------------------- |
| BadRequest   | 400        | 不符合接口要求的请求参数               |
| Conflict     | 409        | 各类资源冲突，例如用户名重复等         |
| Forbidden    | 403        | 没有足够的权限，例如妄图修改别人的信息 |
| NotFound     | 404        | 要找的资源不存在                       |
| Unauthorized | 401        | “黑户”，没有给token，假toekn等         |

> 如果阅读源代码的话，会发现 `XXXAdvice`  `XXXException`  两个文件，他们分别是什么作用呢？
>
> `XXXException`  ：继承原生的 `Exception` ，本项目中主要用于传递 `messsage`
>
> `XXXAdvice`： 处理这个特定异常，在本项目中返回特定格式的 `Response`

#### 自己封装

在编程过程中，可能有一些异常会较为常用，我们可以将其封装。

例如 `UserNotFoundExcption` 是一个较常用的异常，我们可以将其封装，方便调用：

```java
public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(String username) {
    super("用户名 " + username + " 不存在");
  }
}
```

> 来源于 `/exception/user/UserNotFoundException.java`

### dto

> dto全称：data transfer object
>
> 具体参见参考资料或王皓天整理

#### model

##### 模板

因为 java 的限制，我们返回内容时并不能灵活地使用 json，必须创建一个类来实例化。

> 比如我们可能只是需要
>
> ```json
> {
>     username: 'username',
>     email: 'example@example.com'
> }
> ```
>
> 但是我们必须创建一个类……所以就有了 UserDto
>
> ```java
> @Data
> @Accessors(chain = true) 
> public class UserDto {
>   private String email;
>   private String username;
> }
> ```

model 就是这些类的统称，我们可以按照统一地格式来构建这样的 model

```java
@Data
@Accessors(chain = true) 
public class XXXDto {
  private String field1;
  private String field2;
}
```

我们分别解释一下这些注解

##### `@data`

>  `Lombok` 包提供提供一系列注解，帮助我们简化代码
>
> Project Lombok makes java a spicier language by adding 'handlers' that know how to build and compile simple, boilerplate-free, not-quite-java code.

我们构建这样一个类的目的既然是为了传输数据，那么肯定就必须要有 `Getter` `Setter` 等等的成员函数，如果我们自己手写的话，无疑非常麻烦。因此我们引入了 `Lombok`

| 注解名称                 | 功能                                                         |
| ------------------------ | ------------------------------------------------------------ |
| @Setter                  | 自动添加类中所有属性相关的 set 方法                          |
| @Getter                  | 自动添加类中所有属性相关的 get 方法                          |
| @Builder                 | 使得该类可以通过 builder (建造者模式)构建对象                |
| @RequiredArgsConstructor | 生成一个该类的构造方法，禁止无参构造                         |
| @ToString                | 重写该类的toString()方法                                     |
| @EqualsAndHashCode       | 重写该类的equals()和hashCode()方法                           |
| @Data                    | 等价于上面的@Setter、@Getter、@RequiredArgsConstructor、@ToString、@EqualsAndHashCode |

作为一个 "数据类"， 一个简单的 `@Data` 能够帮我们解决很多事情。

##### `@Accessors(chain = true) `

在本项目中你可能会看到很多形如这样的代码：

```java
UserDto userDto = new UserDto()
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setRoles(user.getRoles());
```

为什么他们可以一直“点”呢？就是因为启用了这样一个注解。否则的话，你要不然通过构造函数一次性载入所有数据，要不然：

```java
UserDto userDto = new UserDto();
userDto.setEmail(user.getEmail());
userDto.setUsername(user.getUsername());
userDto.setRoles(user.getRoles());
```

因此这个注解的主要目的也是减少代码量。

#### mapper

mapper 的主要作用是为了从数据库中获取数据 model 所需要的数据，并转成一个 model 实例。所以虽然他需要单独成类，但是内容并不复杂，完成从数据库查出来的对象到所需要的 Dto 模型的映射即可。代码框架如下：

```java
@Component
public class XXXMapper {

  public XXXDto toXXXDto(Xyz xyz) {

    return new XXXDto()
        .setField1(xyz.getField1())
        .setField2(xyz.getField2())
        .setField3(xyz.getField3());
  }
}
```

以 `UserMapper` 为例：

```java
@Component
public class UserMapper {

  public UserDto toUserDto(User user) {

    return new UserDto()
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setRoles(user.getRoles());
  }
}
```

#### assembler

assembler 作为组装器，主要目的是为了拼接出 更符合 `REST` 规范的返回。

比如我们看这样一个样例返回，其中的 `_links`，就是 `REST` 规范规定的返回之一。

```json
{ 
    "email": "user@frogsoft.com",
    "username": "user",
    "roles": [
        "ROLE_USER"
    ],
    "_links": {
        "self": {
            "href": "http://127.0.0.1:8080/v1/users/user"
        },
        "allUsers": {
            "href": "http://127.0.0.1:8080/v1/users/?page=0&size=10"
        }
    }
}
```

但显然这种东西不应该由我们自己指定返回，所以就有了 `assembler` 组装器，帮助我们组装这些内容。

> 但是考虑到大家都很懒，我们也不强求返回很多的内容。
>
> 但是为什么还是保留了 assembler 呢？为了后续的可拓展，我们只是“暂时”不写而已。

构建模板如下：

```java
@Component
public class XyzModelAssembler implements
    RepresentationModelAssembler<XyzDto, EntityModel<XyzDto>> {

  @Override
  public EntityModel<XyzDto> toModel(XyzDto xyzDto) {
    return EntityModel.of(
        xyzDto
    );
  }
}
```

> 你甚至可以直接把 `Xyz` `xyz` 换成你自己的名字就可以使用了
>
> 如果想要看更多用法：
>
> 比如我们先来解析一个样例 `/assembler/user/UserModelAssembler.java`
>
>
> ```java
> @Component
> public class UserModelAssembler implements
>     RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {
> 
>   @Override
>   public EntityModel<UserDto> toModel(UserDto userDto) {
>     return EntityModel.of(
>         userDto,  
>         linkTo(methodOn(UserController.class).getOneUser(userDto.getUsername(), null))
>             .withSelfRel(),
>         linkTo(methodOn(UserController.class).getAllUsers(0, 10)).withRel("allUsers")
>     );
>   }
> }
> ```
>
> 除了熟悉的东西以外，我们看到一些其他的内容：
>
> - `linkTo(method)` 链接到某个控制器的方法 `method`
> - `methodOn(XXXController.class).yyy()`  用这样的格式表示 `XXXController` 下的 `yyy()` 方法 
> - `.withSelfRel()`  在 `_links` 中产生 `self` 字段，即把这个方法链接到自身的 `self`链接
> - `.withRel("string")`  在 `_links` 中产生 `string` 字段，即把这个方法链接到自身的 `string`链接



## 参考资料

1. [Spring Boot 2.0 — Project Structure and Best Practices (Part 2) 项目结构说明](https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3)
2. [Getting Started | Accessing Data with JPA (spring.io) JPA 样例](https://spring.io/guides/gs/accessing-data-jpa/)