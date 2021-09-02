# 使用Spring构建REST规范的项目指南
## 建立Spring项目
- 使用[Spring Initializr](https://start.spring.io/)（Spring提供的依赖包生成网站）
	Dependencies中加入以下依赖
	- Spring Web
	- Spring Data JPA
	- 一个数据库驱动（使用你喜欢的数据库驱动，比如MySQL Driver）
		设置好以后，点击Generate Project，就可以下载到对应的zip格式的空项目包
		![](Spring%E6%9E%84%E5%BB%BAREST%E5%85%A5%E9%97%A8_md_files/image_20210901102458.png?v=1&type=image&token=V1:b4HpYs_VqhXb-oIldnZdJnJ_uv28xUDaAvERdY2YC3I)
- 在IDE中可以使用**Maven**管理项目包（建立Maven项目），只要把对应的包版本按照xml的格式写到项目根目录的pom.xml中就可以了
```xml
<?xml version="1.0" encoding="UTF-8"?>  
<project xmlns="http://maven.apache.org/POM/4.0.0"  
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">  
 <modelVersion>4.0.0</modelVersion>  
 <parent> 
	 <groupId>org.springframework.boot</groupId>  
	 <artifactId>spring-boot-starter-parent</artifactId>  
	 <version>2.5.4</version>  
	 <relativePath/> <!-- lookup parent from repository -->  
 </parent>
 ......
 <dependencies>  
	 <dependency> 
		*添加你的依赖包* 
	 </dependency>
```
## 配置程序主入口
一般来说，主入口文件都命名为`XXXAplication.java`，格式如下
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YourApplication {

  public static void main(String... args) {
    SpringApplication.run(YourApplication.class, args);
  }
}
```
其中`@SpringBootApplication`标识服务端的入口类，`SpringApplication.run(YourApplication.class, args)`使用SpringBoot启动服务，一切配置都由其自动完成。

## 使用JPA链接模型类和数据库
JPA使用ORM（对象关系映射）来方便我们用代码链接数据库，简单来说，我们可以用一个类对应数据库中的一张表，类中的成员对应表中的属性，当类成员的值变化时，让JPA自动同步数据表中的值。这样的类一般称为模型类（MVC中的Model）或实体类，可以存放一些基础数据（比如用户类User）

### 语法说明
导入命名空间：
```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
```

```java
@Entity
class User{
	...
}
```
`@Entity` 注解，写在类前面，声明JPA托管该类
```java
@Id 
@GeneratedValue
private int id;
```
`@Id` 和 `@GeneratedValue`：注解，写在类成员变量前面，声明该变量在数据表中所对应的属性是**主关键字**（PK）
其中`@GeneratedValue`注解向JPA声明主关键字的生成策略
```java
@Id 
@GeneratedValue
private long id;

private String email;  
private String username;
```
对于非主关键字的属性，JPA会自动以类成员变量的命名，生成对应的属性名，无需再加入注解。
```java
User() {}
User(String email,String username) {
	this.email = eamil;
	this.username = username;
}
```
有参构造函数用来创建没有指定id（主关键字）的实例

## 配置数据库的加载

JPA对类数据的操作，都是通过自定义一个新的接口实现的。
建立一个新接口，继承`JpaRepository`接口：
```java
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, long> {
	...
}
```
其中，泛型参数`<User, long>`分别指JPA所链接的类的类型和其主关键字的类型.

接口类中可以自定义增删改查函数，如：
```java
	// 返回一个对象
	User findByUsername(String username);
	// 根据pageable参数返回分页对象（一页的内容）
	Page<User> findAllBy(Pageable pageable);
```
其中，`findByXXXXX`函数，Spring可以根据“XXXXX”的命名，自动对应模型类中同名（除了主键，不区分大小写）的数据变量，帮助你实现查找函数。即程序员只需声明该函数，并写对参数和返回类型。 
`findAllBy`返回一个分页类型的所有数据成员。

然后，建立一个配置类，告诉Spring Boot加载接口对应数据类：
```java
import ...

@Configuration  
public class Initialize {  
  
  @Bean  
  CommandLineRunner init(UserRepository userRepository, ...) 
  {
	  return args -> {
		  userRepository.save(new User()...)
	  };
  }
  
```
其中：
- `@Configuration`标识类是用来配置Spring Boot的加载项的
- `@Bean`标识对象是一个Spring Bean
- `CommandLineRunner`接口：实现该接口的所有*Bean*都会在启动Spirng Boot时被加载。其中传入的参数即为`JpaRepository`类型的自定义接口对象（可以有多个，对应多个数据表），通过这些对象，即可以在服务加载时初始化数据库中内容。

	**在返回的匿名函数内用`JpaRepository`对象对数据表进行初始化操作**：
	使用`xxxRepository.findByXXXX()`函数，查找数据表的条目（前提：现在接口类中定义对应的函数）
	使用`xxxRepository.save()`函数，修改并保存对应repository对应的模型类的成员数据，同时JPA会同步更新数据库的内容
	>注意：如果要操作模型类对象，使用其getter和setter函数，如
	```java
	User user = userRepository.findByUsername("wht")
	String _info = user.getInfo();
	userRepository.save(new User().setUsername("admin")
								  .setInfo(_info)
	)
	```
	> 在模型类中，注意封装好各个成员变量的getter和setter函数

## 实现HTTP控制模块

建立一个新类，用来处理前端请求，为了让Spring识别这个控制类，在类前加入注解`@RestController`
> 加入这些注解的目的是为了标记类为一个Spirng的Bean，Spring Boot帮我们配置好了扫描器，其会在启动时扫描全部带有`@Component`注解的类，将其注册为 Spring Bean。只有这样，Spring才会在有网络请求进入时，通过RequestMapping去索引 *注册过的组件类* 的相应函数。
> `@Component`根据类的功能不同，其又分为以下三个子注解类型：
> @Controller：控制层，标注返回前端的控制组件
> @Service：业务逻辑层，标注中间层的控制组件
> @Repository：DAO层，标注数据库访问的组件

> 而`@RestController`是`@Controller`的一个升级版，其相当于`@Controller  + @ResponseBody`，可以支持返回json格式的数据给前端。在REST模式设计的服务上，应该使用`@RestController`

在控制类中，在函数前加入注解`@RequestMapping({url地址})`，实现路由的作用，即用 URL请求的地址 对应 控制类调用相应的函数

根据REST规范，请求的类型可以分为 GET/POST/PUT/DELETE，因此可以直接使用以下子注解：
`@GetMapping("url")`
`@PostMapping("url")`
`@PutMapping("url")`
`@DeleteMapping("url")`


```java
// EmployeeController.java
import java.util.List;

// 导入RequestMapping使用的命名空间
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController {

  // 引用接口服务，用来实现各种功能，记得要使用final静态变量
  // 比如这里使用定义的JpaRepository接口对象，用来操作数据库
  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }


  // 如果用户请求GET，地址为/employees，调用该函数
  @GetMapping("/employees")
  List<Employee> all() {
    return repository.findAll();
  }

  // 如果用户请求GET，地址为/employees，调用该函数
  @PostMapping("/employees")
  // @RequestBody 用于读取发来的请求中包含的参数数据，注意保持类型一致
  Employee newEmployee(@RequestBody Employee newEmployee) {
    return repository.save(newEmployee);
  }


  @GetMapping("/employees/{id}")
  // @PathVariable 用于读取url地址中包含的参数数据，用{}包起参数名
  Employee one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> 
				    new EmployeeNotFoundException(id));
  }

  // 如果用户请求PUT（修改），地址为/employees/{id}，调用该函数
  @PutMapping("/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        newEmployee.setId(id);
        return repository.save(newEmployee);
      });
  }

  // 如果用户请求DELETE（删除），地址为/employees/{id}，调用该函数
  /employees/{id}
  @DeleteMapping("/employees/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
```

##  异常处理

在我们的后端服务中，一旦出现异常，光进行Java本身的异常处理是不够的，因为这样前端收不到对应的异常消息，没有办法知道接下来要干什么（前端：你这不是摆烂吗这是），因此Spring还提供`ExceptionHandler`反射机制，当后端产生异常时返回错误信息到前端。

### 主要步骤
先创建自己的异常类，继承RuntimeException，定义一种异常，如：
`XXXXException.java`
```java 
// EmployeeNotFoundException.java
class EmployeeNotFoundException extends RuntimeException {

  EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}
```
注意，这个异常类只抛出后端的错误信息，我们还需要让Spring返回前端信息
所以，在该类下再创建一个
`XXXXAdvice.java`

```java
// EmployeeNotFoundAdvice.java
// 导入命名空间
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EmployeeNotFoundAdvice {

  @ResponseBody
  // 反射自定义异常类
  @ExceptionHandler(EmployeeNotFoundException.class)
  // 注解，说明返回时向前端回复错误状态码（HttpStatus.NOT_FOUND 即 404）
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(EmployeeNotFoundException ex) {
	// 返回的错误信息
    return ex.getMessage();
  }
}
```
定义XXXXAdvice类时，使用`@ControllerAdvice`注解，并且在其处理函数（通常命名为XXXXHandler）前，使用`@ResponseBody`、`@ExceptionHandler`、`@ResponseStatus`，它们的作用分别是：
注解 | 作用
-------- | -----
`@ResponseBody` | 说明返回给前端的消息采用json格式
`@ExceptionHandler` | 说明这是一个处理异常的消息（参数填写对应异常类的反射）
`@ResponseStatus` | 说明返回的HTTP状态码（可以使用HttpStatus枚举）


## DTO（Data Transfer Object）支持
### 添加Spring HATEOAS
在`pom.xml`中加入以下依赖部分代码即可
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```
HATEOAS是Spring中用来实现链接（Link）的工具，链接的存在使得客户端可以动态发现其所能执行的动作。

### 链接的作用
让我们来看一个例子。这是一个普通的json返回包：
```json
{
  "id": 1,
  "name": "Bilbo Baggins",
  "role": "burglar",
}
```
这是一个带Links的json返回包：
```json
{
  "id": 1,
  "name": "Bilbo Baggins",
  "role": "burglar",
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees/1"
    },
    "employees": {
      "href": "http://localhost:8080/employees"
    }
  }
}
```
返回的链接 “_links”部分将告诉前端，用户当前的操作（或是所在的位置）可以跳转到哪些可能的地址，因此前端可以根据这些链接为用户提供更方便的操作。

### 返回带链接的数据包
> 注意，以下代码可以简化，写这些的目的是为了更好的理解链接，如果要速成，请跳转到[简化生成链接的步骤](#1)一节

以下是一个向前端返回带链接的数据的例子：
```java
@GetMapping("/employees/{id}")
EntityModel<Employee> one(@PathVariable Long id) {

  Employee employee = repository.findById(id).orElseThrow(
						  () -> new EmployeeNotFoundException(id));

  return EntityModel.of(employee, 
      linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
      linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
}
```
这个Mapping函数和之前的相比，有下面这些不同的地方：

- 返回值类型由自定义的模型类`Employee`变为了`EntityModel<Employee>`。
泛型`EntityModel<T>`是Spring HATEOAS设计的一种容器，其中不仅能存储数据，还可以存储一系列链接。
当你返回值时，不再只返回一个`
Employee employee`的模型对象，而是使用
	```java
	EntityModel.of(employee,{links})
	```
	返回一个`EntityModel`类型（包括employee对象和若干个链接）

- 使用`linkTo()`方法生成链接：
	参数传入一个`methodOn()`函数，参数为你要链接的方法所在的类的反射，一般为控制类，即`XXXXController.class`，然后用`.`点出要连接到的类的方法，如：
	`linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel()`
	即会加入`EmployeeController{}.one(id)`方法所在Mapping的URL，即
	`"localhost:8080/employees/{id}"`
	
	> 可以理解为这是一种反向Mapping，即从调用的方法查找到对应的路由
	
	`linkTo()`返回一个Link对象，可以用其`.withSelfRel()`和`.withRel(Sting)`来修饰其在返回包中的Rel标签
	> Rel 是 relation 的简写，用来说明链接自身和链接之间的关系
	
	- `.withSelfRel()`返回的链接的Rel标签为`“self”：{}`，这说明返回的是请求的链接本身。一般来说带链接的返回包都要返回自己，其目的大概和类里面为啥要有this这个对象一样。
	- `.withRel(Sting)`返回的链接带有一个用String参数修饰的标签，即`“xxxx”：{}`，这个标签有助于前端根据标签的名称所以链接。
	
### Spring HATEOAS的各种容器（Model）
- `EntityModel<>` 用来容纳单个模型对象及其链接
- `CollectionModel<>`用来容纳多个模型对象及其链接，一个Collection可以包含多个Entity，写为：`CollectionModel<EntityModel<T>>`

  将多个实体模型打包成Collection时，最好将每个模型对象都包装好链接，再为整体的Collection（可以是List、Map或是Dict之类）包装链接，示例如下：

```java
@GetMapping("/employees")
CollectionModel<EntityModel<Employee>> all() {

// 这一行写了很多东西，但主要的步骤是：
// find所有employee的对象 -> 转换为java8流对象（stream）-> 用map函数把每个
// employee的对象换为EntityModel对象，打包链接 -> 将所有单体对象用collect函数
// 重新打包进列表
	List<EntityModel<Employee>> employees = repository.findAll().stream()
      .map(employee -> EntityModel.of(employee,
          linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
          linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
      .collect(Collectors.toList());

//返回CollectionModel对象，打包整体的链接
  return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}
```
​	该代码最后返回的json包格式如下：
```json
{
  "_embedded": {
    "employeeList": [
      {
        "id": 1,
        "name": "Bilbo Baggins",
        "role": "burglar",
        "_links": {
          "self": {
            "href": "http://localhost:8080/employees/1"
          },
          "employees": {
            "href": "http://localhost:8080/employees"
          }
        }
      },
      {
        "id": 2,
        "name": "Frodo Baggins",
        "role": "thief",
        "_links": {
          "self": {
            "href": "http://localhost:8080/employees/2"
          },
          "employees": {
            "href": "http://localhost:8080/employees"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees"
    }
  }
}
```
这就是一个非常符合REST规范的资源包了。

- `PagedModel<>`也用来容纳多个模型对象及其链接，并且支持分页，生成PageModel必须使用`Page<>`类型的泛型对象，在生成PageModel时提供一个`Pageable `的分页对象，用来记录分页信息：

  ```java
  // PageModel.of()的函数重载类型：
  
  // 不传入Link参数，所有对象自动返回空Link
  public static <T> PagedModel<T> model = PageModel.of
      (Collection<T> content, @Nullable PagedModel.PageMetadata metadata);
  // 传入一组Link参数，每个Link与content一一对应
  public static <T> PagedModel<T> model = PageModel.of
      (Collection<T> content, @Nullable PagedModel.PageMetadata metadata, Link... links); 
  // 传入一个Link迭代对象，每个Link与content一一对应
  public static <T> PagedModel<T> model = PageModel.of
      (Collection<T> content, @Nullable PagedModel.PageMetadata metadata, Iterable<Link>);
  ```

  带有分页信息的返回包格式如下，可以看到除了CollectionModel的信息外，还另外增加一个”Page“字段，存储分页信息：

  ```json
  {
      "_embedded": {
          "userDtoList": [
              {
                  "email": "admin@frogsoft.com",
                  "username": "admin",
                  "roles": [
                      "ROLE_USER",
                      "ROLE_ADMIN"
                  ],
                  "_links": {
                      "self": {
                          "href": "http://127.0.0.1:8080/v1/users/admin"
                      },
                      "allUsers": {
                          "href": "http://127.0.0.1:8080/v1/users/?page=0&size=10"
                      }
                  }
              }
          ]
      },
      "_links": {
          "self": {
              "href": "http://127.0.0.1:8080/v1/users?page=0&size=10"
          }
      },
      "page": {
          "size": 10,
          "totalElements": 1,
          "totalPages": 1,
          "number": 0
      }
  }
  ```

  可以使用Spring提供的`PagedResourcesAssembler`类，简化`PagedModel<>`的生成，

  *在阅读以下代码之前，先阅读下一节关于`RepresentationModelAssembler`接口的介绍。*

  ```java
  // 使用单个EntityModel的RepresentationModelAssembler接口生成PageModel的函数。
  public PagedModel<EntityModel<T>> getPageModelT(Pageable pageable) {
  	
      // 获取实体类型T对应的PagedResourcesAssembler类型
      private final PagedResourcesAssembler<T> pagedResourcesAssembler;
      
      // 获取实体类型T的RepresentationModelAssembler（自行实现）
      private final XXXXModelAssembler tModelAssembler;
      
      // 将多个实体类型T打包为Page<T>类型（可以使用前面提到的JpaRepository中的findAllBy()实现）
      Page<T> contents = /* find the contents and packed in Page*/
  
      // 调用.toModel返回分页模型，参数分别为打包的Page<T>类型 和 单个T类型对应的RepresentationModelAssembler对象
      return pagedResourcesAssembler.toModel(contents, tModelAssembler);
    }
  
  ```

### 简化生成链接的步骤
<span id="1"></span>
为了减少代码复用，不要在每次创建link时都重复写代码，我们可以写一个函数，将模型对象（比如 `Employee`）转换为对应的`EntityModel<T>`对象。这样当我们需要创建link时，只需简单的调用这个方法。

幸运的是，Spring HATEOAS帮你提供了`RepresentationModelAssembler`接口，通过这个接口，你可以快速创建转换类，而不用自己搭建框架。

实现`RepresentationModelAssembler`接口的方法如下：

```java
//EmployeeModelAssembler.java
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

  @Override
  public EntityModel<Employee> toModel(Employee employee) {

    return EntityModel.of(employee, 
		linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
        linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
  }
}
```

为你的接口实现类起一个 XXXModelAssembler的名字；加入Spring的注入注解`@Component`；实现接口的泛型参数`<Employee, EntityModel<Employee>>`表明其是将模型对象`XXX`转换为`EntityModel<XXX>`

该接口只需要实现（重载）一个`toModel`函数，其内容和之前直接在控制器类的函数中创建链接的代码一模一样。

使用该接口对象的方法和repository接口对象的方法类似，只需在控制器中声明其静态引用：

```java
private final EmployeeModelAssembler assembler;
```

别忘了在有参构造函数中也为其添加参数，然后只要在返回函数中调用`toModel`函数即可：

```java
@GetMapping("/employees/{id}")
EntityModel<Employee> one(@PathVariable Long id) {
    
    ...
        
	return assembler.toModel(employee);
}
```

之前打包Collection的代码也可以简化为：

```java
@GetMapping("/employees")
CollectionModel<EntityModel<Employee>> all() {

  List<EntityModel<Employee>> employees = repository.findAll().stream() 
      .map(assembler::toModel) 
      .collect(Collectors.toList());

  return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}
```

代码简洁了很多呢！



## 使用ResponseEntity返回数据

另一个让你的代码符合REST规范的要点是永远返回合适的Respone

之前返回的json包中的HTTP状态码需要我们自行写入和处理，`ResponseEntity`则帮我们解决了这个问题，同时支持多种格式的返回包数据。

更新上面的POST函数，以便使用`ResponseEntity`类来处理返回值：

```java
@PostMapping("/employees")
ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

  EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

  return ResponseEntity //
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
      .body(entityModel);
}
```

和不使用`ResponseEntity`相比，返回值类型变为`ResponseEntity<EntityModel<Employee>>`，也就是说，要在EntityModel外层，再包装一层ResponseEntity类型的泛型。在不至于混淆的情况下，可以使用`ResponseEntity<?> `自动推导类型。

使用`ResponseEntity.created()`会返回`HTTP 201` 状态码，表示创建Employee的操作成功。当返回这个内容时，前端通常希望能同时获得该操作所对应的URL地址（Location），以便进行下一步操作。由于我们已经在返回包里打包了Link，因此添加响应头非常方便，代码中`.created()`的参数：`entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()`即是将Link的Self部分写入返回包的头部，最后得到的返回数据如下：

```shell
< Location: http://localhost:8080/employees/3
< Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Fri, 10 Aug 2018 19:44:43 GMT
<
{
  "id": 3,
  "firstName": "Samwise",
  "lastName": "Gamgee",
  "role": "gardener",
  "name": "Samwise Gamgee",
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees/3"
    },
    "employees": {
      "href": "http://localhost:8080/employees"
    }
  }
}
```

`ResponseEntity.created()`只返回一个特定的状态（201），

通常情况下，我们使用`ResponseEntity.status()`返回一个自定义状态码的数据包，状态码可以用`HttpStatus`枚举表示。

| HTTP状态码 |          HttpStatus枚举          |     含义     |
| :--------: | :------------------------------: | :----------: |
|    100     |       HttpStatus.CONTINUE        |     继续     |
|    102     |      HttpStatus.PROCESSING       |    处理中    |
|    200     |          HttpStatus.OK           |     正常     |
|    201     |        HttpStatus.CREATED        |    已创建    |
|    202     |       HttpStatus.ACCEPTED        |    已接受    |
|    204     |      HttpStatus.NO_CONTENT       |   已无内容   |
|    302     |         HttpStatus.FOUND         |    已找到    |
|    400     |      HttpStatus.BAD_REQUEST      |   请求错误   |
|    401     |     HttpStatus.UNAUTHORIZED      |    未授权    |
|    403     |       HttpStatus.FORBIDDEN       |   禁止访问   |
|    404     |       HttpStatus.NOT_FOUND       |   无法找到   |
|    408     |    HttpStatus.REQUEST_TIMEOUT    |   请求超时   |
|    429     |   HttpStatus.TOO_MANY_REQUESTS   |   请求过多   |
|    500     | HttpStatus.INTERNAL_SERVER_ERROR | 内部服务错误 |
|    502     |      HttpStatus.BAD_GATEWAY      |   网关错误   |
|    503     |  HttpStatus.SERVICE_UNAVAILABLE  |  服务不可用  |
|    504     |    HttpStatus.GATEWAY_TIMEOUT    |   网关超时   |

```java
ResponseEntity.status(/* http状态码 */HttpStatus.XXX).body(model);
```

其他一些常用的状态，`ResponseEntity`也为我们封装好了以下常用函数：

```java
// ok：返回状态码200
ResponseEntity.ok().body(model);
// badRequest：返回状态码400
ResponseEntity.badRequest().body(model);
// notFound：返回状态码404
ResponseEntity.notFound().body(model);
```



## Controller、Service、Repository 三层结构

之前我们提到，Spring用来注册Bean的控制接口@Component根据名称不同又分为三种：

> @Controller：控制层，标注返回前端的控制组件
> @Service：业务逻辑层，标注中间层的控制组件
> @Repository：DAO层，标注数据库访问的组件

之所以这么设计，是由编写代码中分层规范决定的，通常Controller层最接近前端，Repository(DAO)层最接近后端（数据库），这样的结构最利于代码解耦化，也利于每个部分的代码更加简洁，接下来我们就来整理项目以适应三层结构。

### @Controller控制层

在项目结构中建立一个Controller包，用来处理控制层。

控制层是最接近前端的部分，所以要适应可能随时改变的前端需求。因此最好做好版本控制，可以将Controller包命名为`/Controller.v{版本号}`。当需要修改需求时，可以保留旧版本，重新建立新版本的Controller包，以实现向后兼容。

控制层要实现的内容：

根据设计好的API，建立若干控制类：`XXXXController`，放置于`/Controller/api`包下。

若API需要接受前端请求的数据比较复杂，需要单独用一个数据类存储，可以建立对应的数据类`XXXXRequest`，放置于`/Controller/request`包下。

控制类中要做的事情：

- 路由处理：用`@RequestMapping`索引（这个模块的）根地址（可以写在class的定义前），然后用`@Get/Post/Put/DeleteMapping`索引子地址

- 权限处理：判断访问请求是否具有特殊的权限：

  - token鉴权：从请求中获取username，与授权系统中的列表匹配（见CONTRIBUTING.md）
  - Roles判断：函数前加入注解`@RolesAllowed("身份名")`，自动拒绝没有对应身份权限的用户的访问，判断用户的身份在登陆时由鉴权系统实现

- 处理请求数据：

  - 读请求数据：函数参数中配置`@RequestParam`和`@PathVariable`，获得请求或URL中的数据

  - 读写数据：调用Service层的接口对象

    > 注意，对于数据的具体处理要在Service层实现，Controller层只简单处理和传输参数，并读取结果（Entity对象）

- 返回状态码，用`ResponseEntity.status()`实现

  

### @Service服务层

服务层，即作为一个承上启下的中间部分，用来处理数据、打包数据。

服务层的基本结构：

建立若干服务接口，在`/service`文件夹下，如果功能细分需要多个功能模块，也可以建立子文件夹

服务接口，以`XXXXService`命名，接口的实现类以`XXXXServiceImpl`命名，使用接口的目的是为了多个类访问同一个数据仓库时方便重载和复用。

服务实现类中要做的事情：

- 读写数据：调用Repository层的接口对象，处理数据
  - 读数据：使用findBy系列方法（在Repository层定义）
  - 写数据：使用save方法
- 打包数据：将要返回的数据通过RepresentationModelAssembler接口对象（这也属于持久层）打包为Entity对象，返回给控制层

### @Repository持久层

持久层，用来和数据库进行直接连接。在这里我们使用JPA来建立持久层，数据库连接变得十分方便，但要满足DTO数据模型的要求，因此持久层分为Repository和Dto两个平行层

持久层的基本结构：

建立若干仓库接口，以`XXXXRepository`命名，接口要继承自`JpaRepository`，以实现数据库连接，接口无需实现（Spring金牌服务，帮您实现！）

建立Dto文件夹，其包含三个部分：

- model文件夹：建立Dto数据类，Dto数据类的命名在基本数据实体类后加Dto即可（如`UserDto`）

  - Dto数据类的成员变量内容和基本数据类一模一样，但无需写与数据库连接的注解，数据的连通由mapper类搞定

- mapper文件夹：对于每个Dto类，建立一个Mapper类，以`XXXXMapper`命名。

  mapper类实现一个函数，将基本数据实体类（如`User`）转化为Dto数据类（如`UserDto`）

  > mapper函数的实现方法：new一个Dto对象，然后将使用setter函数配置所有成员即可。之所以在mapper中new对象，也是处于方便解耦和类嵌套之类的目的

- assemler文件夹，对于每个Dto类，建立一个Assembler类，以`XXXXModelAssembler`命名。该类实现`RepresentationModelAssembler`接口。

  > 注意，这里实现`RepresentationModelAssembler`接口时，传入的实体对象要是已经打包好的Dto类对象，不然我费这么大劲打包Dto干啥（感觉在说废话）	

  Assembler类即主要实现`toModel`函数，实现将Dto数据打包为Entity实体对象，这样，服务层只需调用对应的接口实现类，即可以获得符合REST规范的返回数据包

对于基本数据实体类，建立一个`\Model`文件夹存储，使用`@Entity`实现类与数据库的ORM连接。这些即为持久层所需处理的部分。
