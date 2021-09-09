---
title: frogsoft-cms v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
highlight_theme: darkula
headingLevel: 2

---

# frogsoft-cms

> v1.0.0

# v1/AU 权限 

## POST AU01 登录

POST /v1/auth/login

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "username": {
      "type": "string",
      "title": "用户名"
    },
    "password": {
      "type": "string",
      "title": "密码"
    }
  },
  "required": [
    "username",
    "password"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object|false|none|
|» username|body|string|true|none|
|» password|body|string|true|none|

> 返回示例

> 成功

```json
{
  "username": "user",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2MzA0NTcxOTMsImV4cCI6MTYzMDU0MzU5M30.iO_eFoEMqBN1uUW-Td9CxwtOXYcHmf2jb-araEeiTWk"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

状态码 **201**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|none|
|» token|string|true|none|JWT|

## PUT AU03 重置密码

PUT /v1/auth/forget

重置密码：输入用户名+新密码+验证码，重置密码为新密码

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "username": {
      "type": "string",
      "title": "用户名"
    },
    "varyficationcode": {
      "type": "string",
      "title": "验证码",
      "description": "4位数字"
    },
    "newpassword": {
      "type": "string",
      "title": "新密码"
    }
  },
  "required": [
    "username",
    "varyficationcode",
    "newpassword"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» username|body|string|true|none|
|» varyficationcode|body|string|true|4位数字|
|» newpassword|body|string|true|none|

> 返回示例

> 成功

```json
{
  "username": "User001"
}
```

> 验证码错误

```json
{
  "username": "User001",
  "veryficationcode": "7787"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|验证码错误|Inline|

### 返回数据结构

状态码 **400**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|none|
|» veryficationcode|string|true|none|none|

## POST AU02 忘记密码

POST /v1/auth/forget

忘记密码：输入用户名，返回用户邮箱并向邮箱发送验证码

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|query|string|true|none|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "username": "User001",
  "email": "User0001@bupt.edu.cn"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|用户名不存在|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|none|
|» email|string|true|none|none|

状态码 **404**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|该用户名不存在|

# v1/US 用户/US01 整体操作

## GET US0101 获取用户列表

GET /v1/users

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|page|query|string|false|当前页，从0开始（默认0）|
|size|query|string|false|每页的页数（默认10）|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "userDtoList": [
      {
        "email": "v.beuu@wldrfmxd.bg",
        "username": "江杰",
        "roles": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ],
        "avatar": "http://dummyimage.com/100x100"
      },
      {
        "email": "o.ifhbgb@rxiedgimm.mt",
        "username": "徐洋",
        "roles": [
          "ROLE_USER",
          "ROLE_ADMIN"
        ],
        "avatar": "http://dummyimage.com/100x100"
      },
      {
        "email": "w.cjsu@kgzvcjohq.net.cn",
        "username": "邹秀英",
        "roles": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ],
        "avatar": "http://dummyimage.com/100x100"
      },
      {
        "email": "o.oet@pbbhq.pt",
        "username": "陈敏",
        "roles": [
          "ROLE_USER",
          "ROLE_ADMIN"
        ],
        "avatar": "http://dummyimage.com/100x100"
      },
      {
        "email": "j.tqsxo@xhqeherda.td",
        "username": "孟静",
        "roles": [
          "ROLE_USER",
          "ROLE_ADMIN"
        ],
        "avatar": "http://dummyimage.com/100x100"
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://vwc.hu/grbndqn"
    }
  },
  "page": {
    "size": 94,
    "totalElements": 62,
    "totalPages": 86,
    "number": 65
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» userDtoList|[[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» email|string|true|none|none|
|»»» username|string|true|none|none|
|»»» roles|[string]|true|none|none|
|»»» avatar|string|true|none|none|
|» _links|object|true|none|none|
|»» self|object|true|none|none|
|»»» href|string|true|none|none|
|» page|object|true|none|none|
|»» size|integer|true|none|none|
|»» totalElements|integer|true|none|none|
|»» totalPages|integer|true|none|none|
|»» number|integer|true|none|none|

## POST US0102 创建用户

POST /v1/users

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "username": {
      "type": "string",
      "title": "用户名"
    },
    "password": {
      "type": "string",
      "title": "密码"
    },
    "email": {
      "type": "string",
      "title": "注册邮箱"
    },
    "code": {
      "type": "string",
      "title": "验证码"
    }
  },
  "required": [
    "username",
    "password",
    "email",
    "code"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» username|body|string|true|none|
|» password|body|string|true|none|
|» email|body|string|true|none|
|» code|body|string|true|none|

> 返回示例

> 成功创建用户

```json
{
  "username": "User001",
  "email": "example@frogsoft.com"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功创建用户|Inline|

### 返回数据结构

状态码 **201**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|none|
|» email|string|true|none|none|

# v1/US 用户/US02 单一用户基本信息

## DELETE US0203 删除用户

DELETE /v1/users/{username}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|用户名|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|

## GET US0201 获取用户信息（单一）

GET /v1/users/{username}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|none|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "email": "g.yhimpfr@ddmcpvaztm.km",
  "username": "萧静",
  "roles": [
    "ROLE_ADMIN",
    "ROLE_USER"
  ],
  "favoriteArticles": [
    {
      "id": 4,
      "author": {
        "email": "k.xomtx@hemrgxnvk.tel",
        "username": "万强",
        "roles": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ],
        "avatar": "http://dummyimage.com/100x100"
      },
      "status": "BLOCKED",
      "publishDate": "2001-05-22 14:17:50",
      "updateDate": "2004-09-18 17:54:27",
      "views": 8842232563334998,
      "title": "美构路立身较主场山外立权片转称。",
      "description": "速近量根说导走天圆果四情。",
      "cover": "http://dummyimage.com/240x400",
      "likes": 356870385395352,
      "favorites": 1583442799765236
    }
  ],
  "statistics": {
    "publishArticlesNum": 38,
    "viewsNum": 98,
    "likesNum": 63,
    "favoritesNum": 57
  },
  "publishArticles": [
    {
      "id": 3,
      "author": {
        "title": "作者"
      },
      "status": {
        "title": "状态是否屏蔽"
      },
      "publishDate": "1986-10-13 18:01:20",
      "updateDate": "1982-03-23 09:00:14",
      "views": 7987091424487128,
      "title": "只打青知带律精收步机米油际白音。",
      "description": "矿体然易车院口起者影造酸会八难花。",
      "cover": "http://dummyimage.com/720x300",
      "likes": 3517528367259172,
      "favorites": 8893152630832016
    },
    {
      "id": 73,
      "author": {
        "title": "作者"
      },
      "status": {
        "title": "状态是否屏蔽"
      },
      "publishDate": "1979-04-03 22:53:06",
      "updateDate": "2014-04-14 00:39:07",
      "views": 6726604579395886,
      "title": "号值向质真完图子感管史能果记据。",
      "description": "教口厂要即是国分历织斗己深少今同明。",
      "cover": "http://dummyimage.com/250x250",
      "likes": 8850799872319324,
      "favorites": 8551817844900850
    },
    {
      "id": 42,
      "author": {
        "title": "作者"
      },
      "status": {
        "title": "状态是否屏蔽"
      },
      "publishDate": "2017-10-17 07:34:33",
      "updateDate": "1991-05-31 07:22:18",
      "views": 5973356962631818,
      "title": "两果任带天酸那资取周全需地性世。",
      "description": "重声马反北石间进养身部世体易。",
      "cover": "http://dummyimage.com/468x60",
      "likes": 5706931544775186,
      "favorites": 8948611862506942
    },
    {
      "id": 24,
      "author": {
        "title": "作者"
      },
      "status": {
        "title": "状态是否屏蔽"
      },
      "publishDate": "1985-03-20 06:32:49",
      "updateDate": "1973-08-16 22:41:10",
      "views": 7570557983791588,
      "title": "划整观结市你规业取基近里。",
      "description": "两片气称龙道物划山上提具事。",
      "cover": "http://dummyimage.com/88x31",
      "likes": 4653178064678834,
      "favorites": 7752180974345498
    },
    {
      "id": 35,
      "author": {
        "title": "作者"
      },
      "status": {
        "title": "状态是否屏蔽"
      },
      "publishDate": "1978-08-15 13:20:50",
      "updateDate": "1988-06-27 12:45:03",
      "views": 8313092398055214,
      "title": "千个年作东都党段传影斗取。",
      "description": "意后局个切化适全实少世传积加无。",
      "cover": "http://dummyimage.com/120x60",
      "likes": 3299462232717768,
      "favorites": 1541379834458640
    }
  ],
  "publishComment": [
    {
      "id": 7,
      "article": {
        "title": "所在文章",
        "description": "文章id"
      },
      "author": {
        "title": "评论人",
        "description": "用户id"
      },
      "status": "-5)",
      "content": "正周约题为红达委上办无复等布时。族心往构结县权天教代件层约圆边号。人音但去矿价制写去也议信。经门生石做还需没集争何出西。运况如新里度局器机毛象能件国。性利太族着月前南日但深历眼定消市。",
      "publishDateTime": "1980-03-30 21:29:55",
      "parent": 3859830750342144,
      "likes": 707
    },
    {
      "id": 8,
      "article": {
        "title": "所在文章",
        "description": "文章id"
      },
      "author": {
        "title": "评论人",
        "description": "用户id"
      },
      "status": "0)",
      "content": "观七铁意斯路等海议太程白。题品精连作全一火过南青情里准低。率算好科看步群明如备列定马。五电气省了各持老非论西广车们。中转对利很效别酸因阶商术期。",
      "publishDateTime": "2019-07-31 05:06:10",
      "parent": 6322577796559194,
      "likes": 448
    },
    {
      "id": 9,
      "article": {
        "title": "所在文章",
        "description": "文章id"
      },
      "author": {
        "title": "评论人",
        "description": "用户id"
      },
      "status": "1)",
      "content": "气无战万什在用起关毛出当万电结。周性门此业天分便己信温原海。达系除你单由地学专要亲会火极起。明得治现更非学几非保如段政来期。例水状石报品表统领知规展第面。",
      "publishDateTime": "1995-10-15 20:57:19",
      "parent": 7107460782496006,
      "likes": 346
    },
    {
      "id": 10,
      "article": {
        "title": "所在文章",
        "description": "文章id"
      },
      "author": {
        "title": "评论人",
        "description": "用户id"
      },
      "status": "-6)",
      "content": "结日车程取气门主取是张点。道土调段术片持实表布件价可多际而究。权比府象海展强子自派间深上。节时交光满千导面实矿照下风日。集完各根具成该低近海法整全家因各。平角越影石增形阶打用片走毛办色路而。",
      "publishDateTime": "2002-03-16 22:26:04",
      "parent": 8156505154418788,
      "likes": 426
    },
    {
      "id": 11,
      "article": {
        "title": "所在文章",
        "description": "文章id"
      },
      "author": {
        "title": "评论人",
        "description": "用户id"
      },
      "status": "9)",
      "content": "值争许作育型小可统化在斗九质圆须。可经图叫也务些五千而性入果计。压金布准开导容包断构所形区识及克员。前按以区眼上走第周动声量四。也算位所目党所年各却则深压路极。引相成率亲要真清活进而易界。江东前事算听美的连照近基到常层革。",
      "publishDateTime": "1972-12-04 19:01:55",
      "parent": 6806706769618886,
      "likes": 738
    }
  ],
  "historyArticles": [
    {
      "id": 12,
      "time": "2020-04-07 22:24:52",
      "userId": 57,
      "articleId": 65
    },
    {
      "id": 13,
      "time": "2017-03-24 13:43:01",
      "userId": 63,
      "articleId": 86
    },
    {
      "id": 14,
      "time": "2003-11-07 01:34:00",
      "userId": 76,
      "articleId": 47
    }
  ],
  "avatar": "http://dummyimage.com/100x100"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E8%AF%A6%E7%BB%86%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e8%af%a6%e7%bb%86%ef%bc%89)|

## PUT US0202 修改用户信息（基本)

PUT /v1/users/{username}

修改基本信息：仅用户自己可以修改用户名信息，管理员可以修改所有信息

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "email": {
      "type": "string",
      "title": "邮箱"
    },
    "username": {
      "type": "string",
      "title": "用户名"
    },
    "roles": {
      "type": "array",
      "items": {
        "type": "string",
        "enum": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ],
        "x-apifox": {
          "enumDescriptions": {
            "ROLE_ADMIN": "管理员",
            "ROLE_USER": "普通用户"
          }
        }
      },
      "title": "权限列表",
      "uniqueItems": true
    },
    "avatar": {
      "type": "string",
      "title": "头像"
    }
  },
  "required": [
    "email",
    "username",
    "roles",
    "avatar"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|用户名|
|Authorization|header|string|false|JWT token 验证头|
|body|body|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|false|none|

> 返回示例

> 成功

```json
{
  "email": "b.vgxolpakr@mgnppfn.com.cn",
  "username": "姜杰",
  "roles": [
    "ROLE_ADMIN",
    "ROLE_USER"
  ],
  "avatar": "http://dummyimage.com/100x100"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|

# v1/US 用户/US03 单一用户敏感信息

## PUT US0301 修改用户密码

PUT /v1/users/{username}/password

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "oldPassword": {
      "type": "string",
      "title": "旧密码"
    },
    "newPassword": {
      "type": "string",
      "title": "新密码"
    }
  },
  "required": [
    "oldPassword",
    "newPassword"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|none|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» oldPassword|body|string|true|none|
|» newPassword|body|string|true|none|

> 返回示例

> 成功

```json
{
  "username": "admin"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|旧密码错误|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|none|

状态码 **400**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|none|

## PUT US0302 修改邮箱

PUT /v1/users/{username}/email

修改邮箱：需要验证码

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "varyficationcode": {
      "type": "string",
      "title": "验证码",
      "description": "4位数字"
    },
    "newemail": {
      "type": "string",
      "title": "新邮箱"
    }
  },
  "required": [
    "varyficationcode",
    "newemail"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|none|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» varyficationcode|body|string|true|4位数字|
|» newemail|body|string|true|none|

> 返回示例

> 成功

```json
{
  "email": "c.qnvcf@tjeniqj.gn",
  "username": "朱敏",
  "roles": [
    "ROLE_ADMIN"
  ],
  "avatar": "http://dummyimage.com/100x100"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|验证码错误|Inline|

### 返回数据结构

状态码 **400**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» username|string|true|none|用户名|
|» wrongVerifycationCode|string|true|none|错误的验证码|

# v1/US 用户/US04 单一用户浏览信息

## GET US0401 获取历史记录

GET /v1/users/{username}/history

获取分页的历史记录

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|用户名|
|page|query|string|false|分页器：当前页数，从0开始，默认0|
|size|query|string|false|分页器：每页多少篇文章，默认10|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "historyDtoList": [
      {
        "id": 15,
        "time": "2004-02-25 16:46:52",
        "userId": 87,
        "articleId": 15
      },
      {
        "id": 16,
        "time": "1998-01-17 09:15:00",
        "userId": 93,
        "articleId": 44
      },
      {
        "id": 17,
        "time": "2001-10-15 09:23:40",
        "userId": 70,
        "articleId": 68
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://ynwsspnn.dk/tiobsdj"
    }
  },
  "page": {
    "size": 58,
    "totalElements": 17,
    "totalPages": 49,
    "number": 81
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» historyDtoList|[[%E5%8E%86%E5%8F%B2%E8%AE%B0%E5%BD%95%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e5%8e%86%e5%8f%b2%e8%ae%b0%e5%bd%95%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» time|string|true|none|none|
|»»» userId|integer|true|none|用户若删除，则删除历史记录|
|»»» articleId|integer|true|none|文章若删除，则指向“文章已删除”|
|» _links|object|true|none|none|
|»» self|object|true|none|none|
|»»» href|string|true|none|none|
|» page|object|true|none|none|
|»» size|integer|true|none|none|
|»» totalElements|integer|true|none|none|
|»» totalPages|integer|true|none|none|
|»» number|integer|true|none|none|

## GET US0402 获取收藏列表

GET /v1/users/{username}/favors

获取分页的收藏列表

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|用户名|
|page|query|string|false|分页器：当前页数，从0开始，默认0|
|size|query|string|false|分页器：每页多少篇文章，默认10|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "articleDtoList": [
      {
        "id": 3,
        "author": {
          "email": "p.bezxujsjf@udhabsfno.bs",
          "username": "冯磊",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "BLOCKED",
        "publishDate": "1993-06-22 20:40:50",
        "updateDate": "1984-02-26 21:08:54",
        "views": 7258051258383076,
        "title": "所重越步三道手年儿布太积接几飞。",
        "description": "要矿转听需他义平大始已种究。",
        "cover": "http://dummyimage.com/728x90",
        "likes": 2697055176631726,
        "favorites": 3538054440363852
      },
      {
        "id": 90,
        "author": {
          "email": "u.injkcq@ujtzi.dk",
          "username": "沈洋",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "BLOCKED",
        "publishDate": "1975-04-16 02:42:37",
        "updateDate": "1989-05-17 15:58:47",
        "views": 3946062632927328,
        "title": "感太布情在美达数着具多消车命题干为。",
        "description": "都高南收对各五队建细同族组速。",
        "cover": "http://dummyimage.com/88x31",
        "likes": 4545157330760158,
        "favorites": 5177349613763730
      },
      {
        "id": 36,
        "author": {
          "email": "o.ayf@qljdngwipx.so",
          "username": "袁明",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "BLOCKED",
        "publishDate": "1997-08-23 06:55:23",
        "updateDate": "1996-09-05 19:53:46",
        "views": 1312866450085020,
        "title": "向他角称月米把理大养件名定。",
        "description": "制后军车向联经统队制划去织收育维。",
        "cover": "http://dummyimage.com/88x31",
        "likes": 5291908726328532,
        "favorites": 8730773098708896
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://geiduatbh.cx/pyjg"
    }
  },
  "page": {
    "size": 79,
    "totalElements": 41,
    "totalPages": 54,
    "number": 24
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» articleDtoList|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»» publishDate|string|true|none|none|
|»»» updateDate|string|true|none|none|
|»»» views|integer|true|none|none|
|»»» likes|integer|true|none|none|
|»»» favorites|integer|true|none|none|
|»»» title|string|true|none|none|
|»»» description|string|true|none|none|
|»»» cover|string|true|none|none|
|» _links|object|true|none|none|
|»» self|object|true|none|none|
|»»» href|string|true|none|none|
|» page|object|true|none|none|
|»» size|integer|true|none|none|
|»» totalElements|integer|true|none|none|
|»» totalPages|integer|true|none|none|
|»» number|integer|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|

## GET US0403 获取评论列表

GET /v1/users/{username}/comments

获取该用户分页的评论列表

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|username|path|string|true|用户名|
|page|query|string|false|分页器：当前页数，从0开始，默认0|
|size|query|string|false|分页器：每页多少篇文章，默认10|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "commentDtoList": [
      {
        "id": 18,
        "article": {
          "id": 57,
          "author": {
            "title": "作者"
          },
          "status": "BLOCKED",
          "publishDate": "1995-06-21 10:53:27",
          "updateDate": "2017-10-23 05:25:33",
          "views": 5912489807807088,
          "title": "者单反质市子土知书极放变族性。",
          "description": "养者支红状说思象空大根指史。",
          "cover": "http://dummyimage.com/120x240",
          "likes": 6933992446173642,
          "favorites": 853304183285438
        },
        "author": {
          "email": "x.mxvnuxl@vpytwxtbrw.uk",
          "username": "马静",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "8)",
        "content": "指风米况农设重向三极头广构干日信。义关类业细程情东代小子也于记。研主海任等程老压花圆指机存。主关引流次声并报间战体见外。又半思信总也正米标队证别原个要有。",
        "publishDateTime": "1975-02-09 20:37:42",
        "parent": 7220979093262118,
        "likes": 410
      },
      {
        "id": 19,
        "article": {
          "id": 92,
          "author": {
            "title": "作者"
          },
          "status": "BLOCKED",
          "publishDate": "2021-02-27 11:44:29",
          "updateDate": "2008-01-01 16:35:16",
          "views": 181273676141612,
          "title": "但节温写至正步局进思深十。",
          "description": "时律角题厂求信及之新回认加。",
          "cover": "http://dummyimage.com/234x60",
          "likes": 444745279535216,
          "favorites": 3222994087411444
        },
        "author": {
          "email": "p.bldwjxk@kcua.gov",
          "username": "赖磊",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "-10)",
        "content": "完界干众火结件细常时须容今月。里红商切包青现会条委便眼增月响位。响小进积十采色事处消十群情。消几动名务过团打置手区上。我近确给在广情府时难原断层三。",
        "publishDateTime": "2002-01-12 11:32:59",
        "parent": 3000493055964956,
        "likes": 316
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://dfxbv.zr/pxcsixco"
    }
  },
  "page": {
    "size": 37,
    "totalElements": 18,
    "totalPages": 38,
    "number": 96
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» commentDtoList|[[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|评论id|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» article|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» id|integer|true|none|none|
|»»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»»» publishDate|string|true|none|none|
|»»»» updateDate|string|true|none|none|
|»»»» views|integer|true|none|none|
|»»»» likes|integer|true|none|none|
|»»»» favorites|integer|true|none|none|
|»»»» title|string|true|none|none|
|»»»» description|string|true|none|none|
|»»»» cover|string|true|none|none|
|»»» status|[%E8%AF%84%E8%AE%BA%E7%8A%B6%E6%80%81%20Status](#schema%e8%af%84%e8%ae%ba%e7%8a%b6%e6%80%81%20status)|true|none|"Normal": "正常",             "Blocked": "屏蔽"|
|»»» content|string|true|none|none|
|»»» publishDateTime|string|true|none|'yyyy-MM-dd HH:mm:ss'|
|»»» likes|integer|true|none|默认为0|
|»»» parent|integer|true|none|父级评论的id，无父评论的为0|
|» _links|object|true|none|none|
|»» self|object|true|none|none|
|»»» href|string|true|none|none|
|» page|object|true|none|none|
|»» size|integer|true|none|none|
|»» totalElements|integer|true|none|none|
|»» totalPages|integer|true|none|none|
|»» number|integer|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|
|status|NORMAL|
|status|BLOCKED|

# v1/AT 文章/AT01 整体操作

## POST AT0102 创建文章

POST /v1/articles

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "title": {
      "type": "string",
      "title": "标题"
    },
    "description": {
      "type": "string",
      "title": "简介"
    },
    "content": {
      "type": "string",
      "title": "内容"
    },
    "cover": {
      "type": "string",
      "title": "图片url地址"
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
    }
  },
  "required": [
    "title",
    "description",
    "content",
    "cover",
    "status"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E4%BF%AE%E6%94%B9%E8%AF%B7%E6%B1%82%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e4%bf%ae%e6%94%b9%e8%af%b7%e6%b1%82%ef%bc%89)|false|none|

> 返回示例

> 成功

```json
{
  "id": 56,
  "author": {
    "email": "j.kgmlwivcl@xxdxuxnwg.ki",
    "username": "薛超",
    "roles": [
      "ROLE_USER",
      "ROLE_ADMIN"
    ]
  },
  "status": "NORMAL",
  "publishDate": "1973-06-10 07:46:49",
  "updateDate": "2013-05-13 00:37:27",
  "views": 3546955401736910,
  "title": "何我总群好记取示治值队业广非委安时。",
  "description": "相形到此边方下使持比周候且。",
  "cover": "http://dummyimage.com/720x300",
  "likes": 4757315237600576,
  "favorites": 241226656308636,
  "content": "具连很认劳决包石马切斯龙出十调。装速太可及正向究支记七展路水。名场然白打高务还业始和北反节花消。量飞七格万至小化级厂且局油。每基国主着决接五道相图也生王市。每定有月观步问程识采近专。安任期以完习论包家行级领边联线书书。"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E5%88%9B%E5%BB%BA%E8%BF%94%E5%9B%9E%E5%8C%85%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e5%88%9b%e5%bb%ba%e8%bf%94%e5%9b%9e%e5%8c%85%ef%bc%89)|

## GET AT0101 获取文章（批量）

GET /v1/articles

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|search|query|string|false|搜索和search相关的文章|
|sortBy|query|string|false|排序的字段，默认publishDate|
|order|query|string|false|排序的顺序，升序或者降序，默认DESC|
|page|query|string|false|分页器：当前页数，从0开始，默认0|
|size|query|string|false|分页器：每页多少篇文章，默认10|
|author|query|string|false|作者用户名|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "articleDtoList": [
      {
        "id": 63,
        "author": {
          "email": "v.chogtw@xegstoq.ro",
          "username": "贾敏",
          "roles": [
            "ROLE_USER"
          ]
        },
        "status": "NORMAL",
        "publishDate": "2020-04-10 17:33:16",
        "updateDate": "2001-09-26 16:37:18",
        "views": 8666193424137310,
        "title": "办报什为增手局规组象验观本器发相查济。",
        "description": "军美光标济路色运意口红又全。",
        "cover": "http://dummyimage.com/120x240",
        "likes": 7692382170590734,
        "favourites": 6713128192148202
      },
      {
        "id": 49,
        "author": {
          "email": "v.ldeg@mdtdsdcof.la",
          "username": "袁芳",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ]
        },
        "status": "NORMAL",
        "publishDate": "2010-11-04 02:21:32",
        "updateDate": "2006-04-06 03:53:14",
        "views": 6888841634839514,
        "title": "复些入强流世此极果候府研与铁快品置。",
        "description": "高物速非类马同道查海能改每万识料科标。",
        "cover": "http://dummyimage.com/234x60",
        "likes": 7350793968483990,
        "favourites": 6066035298073982
      },
      {
        "id": 36,
        "author": {
          "email": "q.hdvts@kpsyn.et",
          "username": "汪涛",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ]
        },
        "status": "BLOCKED",
        "publishDate": "1990-04-05 11:40:19",
        "updateDate": "2005-04-07 15:24:45",
        "views": 5570970222832796,
        "title": "便料种机备方接只油可适为也派军今口。",
        "description": "总路物向果半周品变没取还精实。",
        "cover": "http://dummyimage.com/120x600",
        "likes": 1819367395072712,
        "favourites": 1990964295052878
      },
      {
        "id": 29,
        "author": {
          "email": "g.pncupha@fpspm.edu",
          "username": "罗敏",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ]
        },
        "status": "NORMAL",
        "publishDate": "2006-03-29 13:25:59",
        "updateDate": "1992-04-25 20:47:17",
        "views": 6405623122111170,
        "title": "酸压南将求被长则少京战许及习以样。",
        "description": "话和张上这着命府京格置公眼要完。",
        "cover": "http://dummyimage.com/120x600",
        "likes": 6033813717234102,
        "favourites": 4849372188925406
      },
      {
        "id": 6,
        "author": {
          "email": "s.bgm@venlau.kr",
          "username": "常刚",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ]
        },
        "status": "NORMAL",
        "publishDate": "2002-03-05 06:51:57",
        "updateDate": "2019-02-28 16:18:33",
        "views": 4237566218219220,
        "title": "消七例带火马过越可东称用声老但属。",
        "description": "得温照龙先农什强易其厂党表设。",
        "cover": "http://dummyimage.com/250x250",
        "likes": 8815244952146634,
        "favourites": 4809402907609482
      }
    ]
  },
  "_links": {
    "self": {
      "href": "pariatur sed anim proident"
    }
  },
  "page": {
    "size": 54,
    "totalElements": 56,
    "totalPages": 91,
    "number": 55
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» articleDtoList|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»» publishDate|string|true|none|none|
|»»» updateDate|string|true|none|none|
|»»» views|integer|true|none|none|
|»»» likes|integer|true|none|none|
|»»» favorites|integer|true|none|none|
|»»» title|string|true|none|none|
|»»» description|string|true|none|none|
|»»» cover|string|true|none|none|
|» _links|object|true|none|none|
|»» self|object|true|none|none|
|»»» href|string|true|none|none|
|» page|object|true|none|none|
|»» size|integer|true|none|none|
|»» totalElements|integer|true|none|none|
|»» totalPages|integer|true|none|none|
|»» number|integer|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|

# v1/AT 文章/AT02 单一操作

## DELETE AT0203 删除文章（单一）

DELETE /v1/articles/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|要删除的文章id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|删除成功|Inline|

### 返回数据结构

## PUT AT0201 修改文章（单一）

PUT /v1/articles/{id}

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "title": {
      "type": "string",
      "title": "标题"
    },
    "description": {
      "type": "string",
      "title": "简介"
    },
    "content": {
      "type": "string",
      "title": "内容"
    },
    "cover": {
      "type": "string",
      "title": "图片url地址"
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
    }
  },
  "required": [
    "title",
    "description",
    "content",
    "cover",
    "status"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|none|
|Authorization|header|string|false|JWT token 验证头|
|body|body|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E4%BF%AE%E6%94%B9%E8%AF%B7%E6%B1%82%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e4%bf%ae%e6%94%b9%e8%af%b7%e6%b1%82%ef%bc%89)|false|none|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E4%BF%AE%E6%94%B9%E8%AF%B7%E6%B1%82%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e4%bf%ae%e6%94%b9%e8%af%b7%e6%b1%82%ef%bc%89)|

## GET AT0201 获取文章（单一）

GET /v1/articles/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|none|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E8%AF%A6%E7%BB%86%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e8%af%a6%e7%bb%86%ef%bc%89)|

# v1/AT 文章/AT03 点赞

## POST AT0301 点赞文章

POST /v1/articles/{id}/like

如果用户已经点赞，返回409

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|文章的id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "likes": 79
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

状态码 **201**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» likes|integer|true|none|当前点赞数量|

## DELETE AT0302 取消点赞

DELETE /v1/articles/{id}/like

如果用户没有点过赞，返回404

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|文章的id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "likes": 78
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

状态码 **201**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» likes|integer|true|none|取消点赞后当前的点赞量|

# v1/AT 文章/AT04 收藏

## DELETE AT0402 取消收藏

DELETE /v1/articles/{id}/favor

如果用户没有收藏过，返回404

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|文章的id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

## POST AT0401收藏文章

POST /v1/articles/{id}/favor

如果用户已经收藏，返回409

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|文章的id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

# v1/AT 文章/AT05 评论管理

## POST AT0501 发表文章评论

POST /v1/articles/{id}/comments

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "content": {
      "type": "string",
      "title": "评论内容"
    },
    "parent": {
      "type": "integer",
      "description": "无父评论的为0",
      "title": "父级评论"
    }
  },
  "required": [
    "content",
    "parent"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|文章的id|
|Authorization|header|string|false|JWT token 验证头|
|body|body|[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E5%88%9B%E5%BB%BA)](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e5%88%9b%e5%bb%ba))|false|none|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功发表评论|[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E8%AF%A6%E7%BB%86%EF%BC%89](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e8%af%a6%e7%bb%86%ef%bc%89)|

## GET AT0502 获取文章评论

GET /v1/articles/{id}/comments

获得一篇文章下的所有评论（分页）

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|文章的id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "commentDtoList": [
      {
        "id": 20,
        "article": {
          "id": 87,
          "author": {
            "email": "s.arfqms@xkzbk.fo",
            "username": "谢娟",
            "roles": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ],
            "avatar": "http://dummyimage.com/100x100"
          },
          "status": "NORMAL",
          "publishDate": "1974-02-18 06:59:33",
          "updateDate": "1985-08-14 18:42:39",
          "views": 8640053289886950,
          "title": "国问进程列口阶千着无八量备解所。",
          "description": "选形习复候才变报将内全界空置按。",
          "cover": "http://dummyimage.com/300x600",
          "likes": 5285199397131298,
          "favorites": 645588873062296
        },
        "author": {
          "email": "t.fsx@bgp.vg",
          "username": "曹涛",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "2)",
        "content": "能出议于都政白路长证经果些。议路称土角志际经者计速层素铁却。重战大许收水上造技求气决。省各新委做任半几京次至年对少你。事走千无已们住南角往区来又自形却文小。许边维美式特以么段示花近提用部计压。",
        "publishDateTime": "1989-02-17 12:27:49",
        "parent": 2040713897656436,
        "likes": 875
      }
    ]
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» commentDtoList|[[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|评论id|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» article|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» id|integer|true|none|none|
|»»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»»» publishDate|string|true|none|none|
|»»»» updateDate|string|true|none|none|
|»»»» views|integer|true|none|none|
|»»»» likes|integer|true|none|none|
|»»»» favorites|integer|true|none|none|
|»»»» title|string|true|none|none|
|»»»» description|string|true|none|none|
|»»»» cover|string|true|none|none|
|»»» status|[%E8%AF%84%E8%AE%BA%E7%8A%B6%E6%80%81%20Status](#schema%e8%af%84%e8%ae%ba%e7%8a%b6%e6%80%81%20status)|true|none|"Normal": "正常",             "Blocked": "屏蔽"|
|»»» content|string|true|none|none|
|»»» publishDateTime|string|true|none|'yyyy-MM-dd HH:mm:ss'|
|»»» likes|integer|true|none|默认为0|
|»»» parent|integer|true|none|父级评论的id，无父评论的为0|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|
|status|NORMAL|
|status|BLOCKED|

# v1/CM 评论

## PUT CM0202 修改评论

PUT /v1/comments/{id}

> Body 请求参数

```json
{
  "type": "string",
  "description": "修改的评论的内容"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|评论的id（唯一)|
|Authorization|header|string|false|JWT token 验证头|
|body|body|string|false|none|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

## DELETE CM0203 删除评论

DELETE /v1/comments/{id}

删除评论：一并删除子评论

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|要删除评论的id|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET CM0201 获取评论（单条）

GET /v1/comments/{id}

获取一条评论的详细信息

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|string|true|评论的id（唯一）|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "id": 21,
  "article": {
    "id": 72,
    "author": {
      "email": "x.fumot@vywe.hk",
      "username": "唐明",
      "roles": [
        "ROLE_ADMIN",
        "ROLE_USER"
      ],
      "avatar": "http://dummyimage.com/100x100"
    },
    "status": "BLOCKED",
    "publishDate": "2001-04-12 23:11:47",
    "updateDate": "1995-06-20 03:53:58",
    "views": 8044969815477416,
    "title": "四七数接华色组劳高统身圆石。",
    "description": "路片个号政空温状格合查化快。",
    "cover": "http://dummyimage.com/234x60",
    "likes": 5946316902679848,
    "favorites": 2030585355152484
  },
  "author": {
    "email": "a.lddwpwvba@arwt.ru",
    "username": "陈磊",
    "roles": [
      "ROLE_USER",
      "ROLE_ADMIN"
    ],
    "avatar": "http://dummyimage.com/100x100"
  },
  "status": "6)",
  "content": "上其工将革可两六几团金毛声例解分局。条由至由识要年义数长提内龙求治通酸复。活严法导听持人对头合酸又该目整。县分安积成过族照县又题么。青名划构当明有济属意程图打下机厂。",
  "publishDateTime": "1973-10-13 13:21:34",
  "parent": 6993960345536564,
  "likes": 270
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E8%AF%A6%E7%BB%86%EF%BC%89](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e8%af%a6%e7%bb%86%ef%bc%89)|

## GET CM0101 获取评论（批量）

GET /v1/comments

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|article|query|string|false|指定查找某篇文章的评论，传入文章id|
|username|query|string|false|指定查找某位用户的评论，传入用户名|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "commentDtoList": [
      {
        "id": 10,
        "article": {
          "id": 83,
          "author": {
            "email": "k.xwirc@sgjopmdve.be",
            "username": "金磊",
            "roles": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ]
          },
          "status": "BLOCKED",
          "publishDate": "2015-08-06 04:54:20",
          "updateDate": "1974-03-13 09:33:59",
          "views": 8075331007770038,
          "title": "器状通音国调计那委新红二原场且成。",
          "description": "外无了事变员位色维程节率界八常共情单。",
          "cover": "http://dummyimage.com/120x600",
          "likes": 3101878220616316,
          "favourites": 7952702411438718
        },
        "author": {
          "email": "n.gpuyevstvp@ebju.nu",
          "username": "段秀英",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ]
        },
        "status": -10,
        "content": "使候回要全金拉积比风去意体水民且。价同代深斯到识拉众老连易照细按。得养内马须委公素东或便合节但派器。在此不机老了加解例飞过然育林。至么先况体论系权传型响标。",
        "publishDate": "2014-07-04 08:03:04",
        "parent": 8637145728587996,
        "likes": 592
      },
      {
        "id": 11,
        "article": {
          "id": 7,
          "author": {
            "email": "l.ncrjfpj@ceztp.edu",
            "username": "曾艳",
            "roles": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ]
          },
          "status": "BLOCKED",
          "publishDate": "2016-09-25 15:04:57",
          "updateDate": "1977-07-26 18:42:25",
          "views": 1081593948042734,
          "title": "过市量青会山那越后却外矿又系方。",
          "description": "部制容近社听铁非华八保现种前治山。",
          "cover": "http://dummyimage.com/728x90",
          "likes": 5952453231525356,
          "favourites": 7972586204450524
        },
        "author": {
          "email": "y.vjrtmpw@vwkjgd.cg",
          "username": "蒋杰",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ]
        },
        "status": 8,
        "content": "六验称极却党在力火多即你军社指。成条议包器由用风组都当该必被中改。连干低称阶且段太类收准圆江往三。了再石始程一边公每群消每与出位外大铁。次好山温确自并新开约群级三强要。",
        "publishDate": "2019-11-24 07:52:03",
        "parent": 6969794779940792,
        "likes": 161
      }
    ]
  },
  "_links": {
    "self": {
      "href": "magna reprehenderit non aliquip"
    }
  },
  "page": {
    "size": 12,
    "totalElements": 48,
    "totalPages": 64,
    "number": 67
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

状态码 **201**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» commentDtoList|[[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|评论id|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» article|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» id|integer|true|none|none|
|»»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»»» publishDate|string|true|none|none|
|»»»» updateDate|string|true|none|none|
|»»»» views|integer|true|none|none|
|»»»» likes|integer|true|none|none|
|»»»» favorites|integer|true|none|none|
|»»»» title|string|true|none|none|
|»»»» description|string|true|none|none|
|»»»» cover|string|true|none|none|
|»»» status|[%E8%AF%84%E8%AE%BA%E7%8A%B6%E6%80%81%20Status](#schema%e8%af%84%e8%ae%ba%e7%8a%b6%e6%80%81%20status)|true|none|"Normal": "正常",             "Blocked": "屏蔽"|
|»»» content|string|true|none|none|
|»»» publishDateTime|string|true|none|'yyyy-MM-dd HH:mm:ss'|
|»»» likes|integer|true|none|默认为0|
|»»» parent|integer|true|none|父级评论的id，无父评论的为0|
|» _links|object|true|none|none|
|»» self|object|true|none|none|
|»»» href|string|true|none|none|
|» page|object|true|none|none|
|»» size|integer|true|none|none|
|»» totalElements|integer|true|none|none|
|»» totalPages|integer|true|none|none|
|»» number|integer|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|
|status|NORMAL|
|status|BLOCKED|

# v1/HM 首页

## GET HM0201 获取每日推荐

GET /v1/home/daily

返回一篇管理员推荐文章

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "id": 21,
  "author": {
    "email": "m.cascmwn@qtcjil.eg",
    "username": "邱敏",
    "roles": [
      "ROLE_ADMIN",
      "ROLE_USER"
    ],
    "avatar": "http://dummyimage.com/100x100"
  },
  "status": "BLOCKED",
  "publishDate": "1984-05-06 09:13:42",
  "updateDate": "2002-06-06 07:47:27",
  "views": 5340332742284518,
  "title": "油确处自马山即声设象强展和。",
  "description": "温个备你院将格会将质器海党想。",
  "cover": "http://dummyimage.com/300x600",
  "likes": 8995874879157538,
  "favorites": 7543923023478136
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|

## PUT HM0202 修改每日推荐

PUT /v1/home/daily

管理员修改推荐文章

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "articleId": {
      "type": "integer",
      "title": "文章id",
      "description": "要修改公告为哪一个文章的id"
    }
  },
  "required": [
    "articleId"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» articleId|body|integer|true|要修改公告为哪一个文章的id|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|数据为空|Inline|

### 返回数据结构

## GET HM03 热门文章

GET /v1/home/hot-articles

获得文章排行榜

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*嵌入信息*

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» articleDtoList|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»» publishDate|string|true|none|none|
|»»» updateDate|string|true|none|none|
|»»» views|integer|true|none|none|
|»»» likes|integer|true|none|none|
|»»» favorites|integer|true|none|none|
|»»» title|string|true|none|none|
|»»» description|string|true|none|none|
|»»» cover|string|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|

## GET HM01 推荐阅读

GET /v1/home/recommendations

根据登录用户的信息，生成推荐阅读列表

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "articleDtoList": [
      {
        "id": 4,
        "author": {
          "email": "w.dbteifpgxy@zdjteop.sn",
          "username": "邱强",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "BLOCKED",
        "publishDate": "2002-07-06 09:03:05",
        "updateDate": "1994-03-05 05:39:20",
        "views": 4454686023856346,
        "title": "斯风三数选低计改流度组半。",
        "description": "指见的参第然细高最如厂华得各前。",
        "cover": "http://dummyimage.com/180x150",
        "likes": 1067921173176642,
        "favorites": 5158842725413160
      },
      {
        "id": 48,
        "author": {
          "email": "x.rivezbj@kwxqfufrwp.gt",
          "username": "苏娟",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "NORMAL",
        "publishDate": "1985-08-19 23:37:49",
        "updateDate": "1972-11-05 12:56:29",
        "views": 4006403688188978,
        "title": "下观斯元西验验于地求意点形。",
        "description": "五亲且等常料角相命去路矿事。",
        "cover": "http://dummyimage.com/240x400",
        "likes": 5869265797560694,
        "favorites": 8543275637312788
      },
      {
        "id": 60,
        "author": {
          "email": "n.xgrvbnfmkk@ecqyxs.mg",
          "username": "邵秀英",
          "roles": [
            "ROLE_ADMIN"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "NORMAL",
        "publishDate": "1997-10-12 18:47:00",
        "updateDate": "1989-01-02 04:58:14",
        "views": 7147236943875828,
        "title": "青高目验头革先及些始教儿真七。",
        "description": "劳候革习置军管群活价住江件技原手都标。",
        "cover": "http://dummyimage.com/336x280",
        "likes": 5780654948518002,
        "favorites": 3191986192721772
      },
      {
        "id": 82,
        "author": {
          "email": "b.uugzwvhq@xdiu.th",
          "username": "汪丽",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "NORMAL",
        "publishDate": "1995-08-30 04:21:13",
        "updateDate": "1970-09-12 17:16:14",
        "views": 2227039608965632,
        "title": "天角报半高着列据米目最着本快通格。",
        "description": "龙难阶统能速片看示开族式人。",
        "cover": "http://dummyimage.com/336x280",
        "likes": 3458101859042682,
        "favorites": 6098851391439246
      }
    ]
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*嵌入信息*

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» articleDtoList|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»» publishDate|string|true|none|none|
|»»» updateDate|string|true|none|none|
|»»» views|integer|true|none|none|
|»»» likes|integer|true|none|none|
|»»» favorites|integer|true|none|none|
|»»» title|string|true|none|none|
|»»» description|string|true|none|none|
|»»» cover|string|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|

## GET HM0401 获取本站公告

GET /v1/home/announcements

获得一个最近公告文章的列表

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "articleDtoList": [
      {
        "id": 16,
        "author": {
          "email": "o.eku@geastg.jo",
          "username": "黄娟",
          "roles": [
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "NORMAL",
        "publishDate": "1974-06-28 20:25:09",
        "updateDate": "1973-07-29 14:31:58",
        "views": 5497355854787528,
        "title": "定史置想世列今改认引边至达。",
        "description": "系上期属社响通好北力格拉回众多克场。",
        "cover": "http://dummyimage.com/240x400",
        "likes": 1463636362984390,
        "favorites": 8383942457230498,
        "content": "常题都见马例无经于易要我局局到。基民了回运来查通真专局王国极已。花南联定快其利江基而样风许段。"
      },
      {
        "id": 49,
        "author": {
          "email": "p.nluw@poibhbg.ke",
          "username": "侯芳",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ],
          "avatar": "http://dummyimage.com/100x100"
        },
        "status": "BLOCKED",
        "publishDate": "1977-09-12 02:51:31",
        "updateDate": "2008-02-26 18:22:52",
        "views": 8377250449515758,
        "title": "目机信党对片器存走总山铁路在王米。",
        "description": "等本按热示共南进个山条五做。",
        "cover": "http://dummyimage.com/240x400",
        "likes": 4083015046284692,
        "favorites": 6988421467793560,
        "content": "过确且图系克电千问海记压长很行。府美下在团种质指花农极复事其本。准广精里意时斗存容整安感教上往。济约变转制党成片应别转况加。包色只角七手验叫更社规院物争八自因。但般电住反口是中开思文常构。"
      }
    ]
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

*嵌入信息*

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» articleDtoList|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E5%88%9B%E5%BB%BA%E8%BF%94%E5%9B%9E%E5%8C%85%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e5%88%9b%e5%bb%ba%e8%bf%94%e5%9b%9e%e5%8c%85%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»» publishDate|string|true|none|none|
|»»» updateDate|string|true|none|none|
|»»» views|integer|true|none|none|
|»»» likes|integer|true|none|none|
|»»» favorites|integer|true|none|none|
|»»» title|string|true|none|none|
|»»» description|string|true|none|none|
|»»» cover|string|true|none|none|
|»»» content|string|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|

## PUT HM0402 修改本站公告

PUT /v1/home/announcements

管理员修改公告列表指向的文章

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "articleIds": {
      "type": "array",
      "items": {
        "type": "integer"
      },
      "description": "要修改的文章id列表"
    }
  },
  "required": [
    "articleIds"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» articleIds|body|[integer]|true|要修改的文章id列表|

> 返回示例

> 成功

```json
{
  "_embedded": {
    "articleDtoList": [
      {
        "id": 32,
        "author": {
          "email": "m.fmujxff@qmfrkqvgff.za",
          "username": "曾强",
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ]
        },
        "status": "BLOCKED",
        "publishDate": "2014-12-19 22:48:29",
        "updateDate": "2001-12-15 04:19:33",
        "views": 7053407393209506,
        "title": "以为始记不一完相连周动花。",
        "description": "容应达用后者教就位时入律。",
        "cover": "http://dummyimage.com/120x90",
        "likes": 6192060761991638,
        "favorites": 837092547595384
      },
      {
        "id": 31,
        "author": {
          "email": "v.mmm@yvd.ch",
          "username": "毛军",
          "roles": [
            "ROLE_ADMIN",
            "ROLE_USER"
          ]
        },
        "status": "NORMAL",
        "publishDate": "2013-01-26 00:37:19",
        "updateDate": "1984-09-19 21:38:43",
        "views": 8447346432658838,
        "title": "收资半选改备置广七作速规路养分派。",
        "description": "劳那已化须业身级装原员须圆速周。",
        "cover": "http://dummyimage.com/728x90",
        "likes": 5339509338203292,
        "favorites": 3705566972367396
      }
    ]
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|成功|Inline|

### 返回数据结构

状态码 **201**

*嵌入信息*

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» _embedded|object|true|none|none|
|»» articleDtoList|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|none|
|»»» id|integer|true|none|none|
|»»» author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»»»» email|string|true|none|none|
|»»»» username|string|true|none|none|
|»»»» roles|[string]|true|none|none|
|»»»» avatar|string|true|none|none|
|»»» status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|»»» publishDate|string|true|none|none|
|»»» updateDate|string|true|none|none|
|»»» views|integer|true|none|none|
|»»» likes|integer|true|none|none|
|»»» favorites|integer|true|none|none|
|»»» title|string|true|none|none|
|»»» description|string|true|none|none|
|»»» cover|string|true|none|none|

#### 枚举值

|属性|值|
|---|---|
|status|NORMAL|
|status|BLOCKED|

# v1/GL 全局

## POST GL02 发送邮件

POST /v1/global/email

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|email|query|string|false|none|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

# v1/GL 全局/GL03 全局配置

## GET GL0301 获取全局配置

GET /v1/global/config

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%EF%BC%88%E8%AF%A6%E7%BB%86%EF%BC%89](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%ef%bc%88%e8%af%a6%e7%bb%86%ef%bc%89)|

## PUT GL0302 修改全局配置

PUT /v1/global/config

> Body 请求参数

```json
{
  "type": "object",
  "properties": {
    "favicon": {
      "type": "string",
      "description": "网站图标 url",
      "title": "站点图标"
    },
    "title": {
      "type": "string",
      "description": "网站名",
      "title": "站点名称"
    },
    "logo": {
      "type": "string",
      "title": "主logo"
    },
    "emailDto": {
      "type": "object",
      "properties": {
        "password": {
          "type": "string",
          "title": "密码"
        },
        "account": {
          "type": "string",
          "title": "账号"
        },
        "body": {
          "type": "string",
          "title": "邮件内容",
          "description": "一串html代码，其中放验证码的地方为：[[${verifyCode}]]"
        },
        "title": {
          "type": "string",
          "title": "邮件主题"
        },
        "host": {
          "type": "string",
          "title": "邮箱HOST",
          "description": "邮箱HOST，如： smtp.exmail.qq.com"
        },
        "port": {
          "type": "string",
          "title": "邮箱PORT",
          "description": "邮箱HOST的PORT，例如465"
        }
      },
      "required": [
        "password",
        "account",
        "body",
        "title",
        "host",
        "port"
      ],
      "title": "验证码邮箱"
    },
    "headerDto": {
      "type": "object",
      "properties": {
        "logo": {
          "type": "string",
          "title": "页眉logo uri"
        }
      },
      "title": "页眉信息",
      "required": [
        "logo"
      ]
    },
    "footerDto": {
      "type": "object",
      "properties": {
        "logo": {
          "type": "string",
          "title": "页眉脚ogo uri"
        }
      },
      "title": "页眉信息",
      "required": [
        "logo"
      ]
    }
  },
  "required": [
    "favicon",
    "title",
    "logo",
    "emailDto",
    "headerDto",
    "footerDto"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%EF%BC%88%E8%AF%A6%E7%BB%86%EF%BC%89](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%ef%bc%88%e8%af%a6%e7%bb%86%ef%bc%89)|false|none|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

## GET GL0303 获取前端配置

GET /v1/global/config/frontend-user

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[%E5%89%8D%E7%AB%AF%E9%85%8D%E7%BD%AE](#schema%e5%89%8d%e7%ab%af%e9%85%8d%e7%bd%ae)|

# v1/GL 全局/GL04 文件操作

## DELETE GL0402 删除已上传文件

DELETE /v1/global/files

将`文章id`对应的文件删除

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» id|number|true|none|none|

## POST GL0401 上传文件

POST /v1/global/files

将文件存储在 /files/{owner ID}/{filename}

> Body 请求参数

```yaml
type: object
properties:
  file:
    type: string
    example: file://C:\Users\FrogDar\Pictures\frogsoft-white.png
    format: binary

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|Authorization|header|string|false|JWT token 验证头|
|body|body|object|false|none|
|» file|body|string(binary)|false|none|

> 返回示例

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|» user|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|»» email|string|true|none|none|
|»» username|string|true|none|none|
|»» roles|[string]|true|none|none|
|»» avatar|string|true|none|none|
|» uri|string|true|none|none|

# 数据模型

<h2 id="tocS_用户信息（批量返回）">用户信息（批量返回）</h2>
<!-- backwards compatibility -->
<a id="schema用户信息（批量返回）"></a>
<a id="schema_用户信息（批量返回）"></a>
<a id="tocS用户信息（批量返回）"></a>
<a id="tocs用户信息（批量返回）"></a>

```json
{
  "type": "object",
  "properties": {
    "email": {
      "type": "string",
      "title": "邮箱"
    },
    "username": {
      "type": "string",
      "title": "用户名"
    },
    "roles": {
      "type": "array",
      "items": {
        "type": "string",
        "enum": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ],
        "x-apifox": {
          "enumDescriptions": {
            "ROLE_ADMIN": "管理员",
            "ROLE_USER": "普通用户"
          }
        }
      },
      "title": "权限列表",
      "uniqueItems": true
    },
    "avatar": {
      "type": "string",
      "title": "头像"
    }
  },
  "required": [
    "email",
    "username",
    "roles",
    "avatar"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|email|string|true|none|none|
|username|string|true|none|none|
|roles|[string]|true|none|none|
|avatar|string|true|none|none|

<h2 id="tocS_用户信息（详细）">用户信息（详细）</h2>
<!-- backwards compatibility -->
<a id="schema用户信息（详细）"></a>
<a id="schema_用户信息（详细）"></a>
<a id="tocS用户信息（详细）"></a>
<a id="tocs用户信息（详细）"></a>

```json
{
  "type": "object",
  "properties": {
    "email": {
      "type": "string",
      "title": "邮箱"
    },
    "avatar": {
      "type": "string",
      "title": "头像"
    },
    "username": {
      "type": "string",
      "title": "用户名"
    },
    "roles": {
      "type": "array",
      "items": {
        "type": "string",
        "enum": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ],
        "x-apifox": {
          "enumDescriptions": {
            "ROLE_ADMIN": "管理员",
            "ROLE_USER": "普通用户"
          }
        }
      },
      "title": "权限列表",
      "uniqueItems": true
    },
    "favoriteArticles": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "title": "文章id"
          },
          "author": {
            "type": "object",
            "properties": {
              "email": {
                "type": "string",
                "title": "邮箱"
              },
              "username": {
                "type": "string",
                "title": "用户名"
              },
              "roles": {
                "type": "array",
                "items": {
                  "type": "string",
                  "enum": [
                    "ROLE_ADMIN",
                    "ROLE_USER"
                  ],
                  "x-apifox": {
                    "enumDescriptions": {
                      "ROLE_ADMIN": "管理员",
                      "ROLE_USER": "普通用户"
                    }
                  }
                },
                "title": "权限列表",
                "uniqueItems": true
              },
              "avatar": {
                "type": "string",
                "title": "头像"
              }
            },
            "required": [
              "email",
              "username",
              "roles",
              "avatar"
            ]
          },
          "status": {
            "type": "string",
            "title": "状态",
            "enum": [
              "NORMAL",
              "BLOCKED"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "NORMAL": "正常",
                "BLOCKED": "屏蔽"
              }
            },
            "default": "NORMAL",
            "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
          },
          "publishDate": {
            "type": "string",
            "title": "创建时间"
          },
          "updateDate": {
            "type": "string",
            "title": "更新时间"
          },
          "views": {
            "type": "integer",
            "title": "被浏览次数"
          },
          "likes": {
            "type": "integer",
            "title": "点赞量"
          },
          "favorites": {
            "type": "integer",
            "title": "收藏量"
          },
          "title": {
            "type": "string",
            "title": "标题"
          },
          "description": {
            "type": "string",
            "title": "简介"
          },
          "cover": {
            "type": "string",
            "title": "图片url地址"
          }
        },
        "required": [
          "id",
          "author",
          "status",
          "publishDate",
          "updateDate",
          "views",
          "title",
          "description",
          "cover",
          "likes",
          "favorites"
        ]
      },
      "title": "收藏",
      "description": "收藏的文章"
    },
    "publishArticles": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "title": "文章id"
          },
          "author": {
            "type": "object",
            "properties": {
              "email": {
                "type": "string",
                "title": "邮箱"
              },
              "username": {
                "type": "string",
                "title": "用户名"
              },
              "roles": {
                "type": "array",
                "items": {
                  "type": "string",
                  "enum": [
                    "ROLE_ADMIN",
                    "ROLE_USER"
                  ],
                  "x-apifox": {
                    "enumDescriptions": {
                      "ROLE_ADMIN": "管理员",
                      "ROLE_USER": "普通用户"
                    }
                  }
                },
                "title": "权限列表",
                "uniqueItems": true
              },
              "avatar": {
                "type": "string",
                "title": "头像"
              }
            },
            "required": [
              "email",
              "username",
              "roles",
              "avatar"
            ]
          },
          "status": {
            "type": "string",
            "title": "状态",
            "enum": [
              "NORMAL",
              "BLOCKED"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "NORMAL": "正常",
                "BLOCKED": "屏蔽"
              }
            },
            "default": "NORMAL",
            "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
          },
          "publishDate": {
            "type": "string",
            "title": "创建时间"
          },
          "updateDate": {
            "type": "string",
            "title": "更新时间"
          },
          "views": {
            "type": "integer",
            "title": "被浏览次数"
          },
          "likes": {
            "type": "integer",
            "title": "点赞量"
          },
          "favorites": {
            "type": "integer",
            "title": "收藏量"
          },
          "title": {
            "type": "string",
            "title": "标题"
          },
          "description": {
            "type": "string",
            "title": "简介"
          },
          "cover": {
            "type": "string",
            "title": "图片url地址"
          }
        },
        "required": [
          "id",
          "author",
          "status",
          "publishDate",
          "updateDate",
          "views",
          "title",
          "description",
          "cover",
          "likes",
          "favorites"
        ]
      },
      "title": "创建文章",
      "description": "创建的文章"
    },
    "publishComment": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "title": "评论id",
            "description": "评论id"
          },
          "author": {
            "type": "object",
            "properties": {
              "email": {
                "type": "string",
                "title": "邮箱"
              },
              "username": {
                "type": "string",
                "title": "用户名"
              },
              "roles": {
                "type": "array",
                "items": {
                  "type": "string",
                  "enum": [
                    "ROLE_ADMIN",
                    "ROLE_USER"
                  ],
                  "x-apifox": {
                    "enumDescriptions": {
                      "ROLE_ADMIN": "管理员",
                      "ROLE_USER": "普通用户"
                    }
                  }
                },
                "title": "权限列表",
                "uniqueItems": true
              },
              "avatar": {
                "type": "string",
                "title": "头像"
              }
            },
            "required": [
              "email",
              "username",
              "roles",
              "avatar"
            ]
          },
          "article": {
            "type": "object",
            "properties": {
              "id": {
                "type": "integer",
                "title": "文章id"
              },
              "author": {
                "type": "object",
                "properties": {
                  "email": {
                    "type": "string",
                    "title": "邮箱"
                  },
                  "username": {
                    "type": "string",
                    "title": "用户名"
                  },
                  "roles": {
                    "type": "array",
                    "items": {
                      "type": "string",
                      "enum": [
                        "ROLE_ADMIN",
                        "ROLE_USER"
                      ],
                      "x-apifox": {
                        "enumDescriptions": {
                          "ROLE_ADMIN": "管理员",
                          "ROLE_USER": "普通用户"
                        }
                      }
                    },
                    "title": "权限列表",
                    "uniqueItems": true
                  },
                  "avatar": {
                    "type": "string",
                    "title": "头像"
                  }
                },
                "required": [
                  "email",
                  "username",
                  "roles",
                  "avatar"
                ]
              },
              "status": {
                "type": "string",
                "title": "状态",
                "enum": [
                  "NORMAL",
                  "BLOCKED"
                ],
                "x-apifox": {
                  "enumDescriptions": {
                    "NORMAL": "正常",
                    "BLOCKED": "屏蔽"
                  }
                },
                "default": "NORMAL",
                "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
              },
              "publishDate": {
                "type": "string",
                "title": "创建时间"
              },
              "updateDate": {
                "type": "string",
                "title": "更新时间"
              },
              "views": {
                "type": "integer",
                "title": "被浏览次数"
              },
              "likes": {
                "type": "integer",
                "title": "点赞量"
              },
              "favorites": {
                "type": "integer",
                "title": "收藏量"
              },
              "title": {
                "type": "string",
                "title": "标题"
              },
              "description": {
                "type": "string",
                "title": "简介"
              },
              "cover": {
                "type": "string",
                "title": "图片url地址"
              }
            },
            "required": [
              "id",
              "author",
              "status",
              "publishDate",
              "updateDate",
              "views",
              "title",
              "description",
              "cover",
              "likes",
              "favorites"
            ]
          },
          "status": {
            "type": "string",
            "title": "状态",
            "enum": [
              "NORMAL",
              "BLOCKED"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "NORMAL": "正常",
                "BLOCKED": "屏蔽"
              }
            },
            "default": "NORMAL",
            "description": "\"Normal\": \"正常\",             \"Blocked\": \"屏蔽\""
          },
          "content": {
            "type": "string",
            "title": "评论内容"
          },
          "publishDateTime": {
            "type": "string",
            "title": "发表时间",
            "description": "'yyyy-MM-dd HH:mm:ss'"
          },
          "likes": {
            "type": "integer",
            "title": "点赞数量",
            "description": "默认为0"
          },
          "parent": {
            "type": "integer",
            "title": "父级评论",
            "description": "父级评论的id，无父评论的为0"
          }
        },
        "required": [
          "id",
          "article",
          "author",
          "status",
          "content",
          "publishDateTime",
          "parent",
          "likes"
        ]
      },
      "title": "评论",
      "description": "发表的评论"
    },
    "historyArticles": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "title": "历史记录id"
          },
          "time": {
            "type": "string",
            "title": "浏览时间"
          },
          "userId": {
            "type": "integer",
            "title": "指向的用户id",
            "description": "用户若删除，则删除历史记录"
          },
          "articleId": {
            "type": "integer",
            "title": "指向的文章id",
            "description": "文章若删除，则指向“文章已删除”"
          }
        },
        "required": [
          "id",
          "time",
          "userId",
          "articleId"
        ]
      },
      "title": "历史",
      "description": "浏览文章的历史记录"
    },
    "statistics": {
      "type": "object",
      "properties": {
        "publishArticlesNum": {
          "type": "integer",
          "title": "文章发布量"
        },
        "viewsNum": {
          "type": "integer",
          "title": "文章总被阅读量"
        },
        "likesNum": {
          "type": "number",
          "title": "文章总被点赞数"
        },
        "favoritesNum": {
          "type": "number",
          "title": "文章总被收藏量"
        }
      },
      "required": [
        "publishArticlesNum",
        "viewsNum",
        "likesNum",
        "favoritesNum"
      ]
    }
  },
  "required": [
    "email",
    "username",
    "roles",
    "favoriteArticles",
    "statistics",
    "publishArticles",
    "publishComment",
    "historyArticles",
    "avatar"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|email|string|true|none|none|
|avatar|string|true|none|none|
|username|string|true|none|none|
|roles|[string]|true|none|none|
|favoriteArticles|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|收藏的文章|
|publishArticles|[[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|创建的文章|
|publishComment|[[%E8%AF%84%E8%AE%BA%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e8%af%84%e8%ae%ba%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|发表的评论|
|historyArticles|[[%E5%8E%86%E5%8F%B2%E8%AE%B0%E5%BD%95%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e5%8e%86%e5%8f%b2%e8%ae%b0%e5%bd%95%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)]|true|none|浏览文章的历史记录|
|statistics|object|true|none|none|
|» publishArticlesNum|integer|true|none|none|
|» viewsNum|integer|true|none|none|
|» likesNum|number|true|none|none|
|» favoritesNum|number|true|none|none|

<h2 id="tocS_历史记录（批量返回）">历史记录（批量返回）</h2>
<!-- backwards compatibility -->
<a id="schema历史记录（批量返回）"></a>
<a id="schema_历史记录（批量返回）"></a>
<a id="tocS历史记录（批量返回）"></a>
<a id="tocs历史记录（批量返回）"></a>

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "title": "历史记录id"
    },
    "time": {
      "type": "string",
      "title": "浏览时间"
    },
    "userId": {
      "type": "integer",
      "title": "指向的用户id",
      "description": "用户若删除，则删除历史记录"
    },
    "articleId": {
      "type": "integer",
      "title": "指向的文章id",
      "description": "文章若删除，则指向“文章已删除”"
    }
  },
  "required": [
    "id",
    "time",
    "userId",
    "articleId"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|id|integer|true|none|none|
|time|string|true|none|none|
|userId|integer|true|none|用户若删除，则删除历史记录|
|articleId|integer|true|none|文章若删除，则指向“文章已删除”|

<h2 id="tocS_文章信息（修改请求）">文章信息（修改请求）</h2>
<!-- backwards compatibility -->
<a id="schema文章信息（修改请求）"></a>
<a id="schema_文章信息（修改请求）"></a>
<a id="tocS文章信息（修改请求）"></a>
<a id="tocs文章信息（修改请求）"></a>

```json
{
  "type": "object",
  "properties": {
    "title": {
      "type": "string",
      "title": "标题"
    },
    "description": {
      "type": "string",
      "title": "简介"
    },
    "content": {
      "type": "string",
      "title": "内容"
    },
    "cover": {
      "type": "string",
      "title": "图片url地址"
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
    }
  },
  "required": [
    "title",
    "description",
    "content",
    "cover",
    "status"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|title|string|true|none|none|
|description|string|true|none|none|
|content|string|true|none|none|
|cover|string|true|none|none|
|status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|

<h2 id="tocS_文章状态 Status">文章状态 Status</h2>
<!-- backwards compatibility -->
<a id="schema文章状态 status"></a>
<a id="schema_文章状态 Status"></a>
<a id="tocS文章状态 status"></a>
<a id="tocs文章状态 status"></a>

```json
{
  "type": "string",
  "title": "状态",
  "enum": [
    "NORMAL",
    "BLOCKED"
  ],
  "x-apifox": {
    "enumDescriptions": {
      "NORMAL": "正常",
      "BLOCKED": "屏蔽"
    }
  },
  "default": "NORMAL",
  "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
}

```

状态

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|状态|string|false|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|

#### 枚举值

|属性|值|
|---|---|
|状态|NORMAL|
|状态|BLOCKED|

<h2 id="tocS_全局配置 - 验证码邮箱">全局配置 - 验证码邮箱</h2>
<!-- backwards compatibility -->
<a id="schema全局配置 - 验证码邮箱"></a>
<a id="schema_全局配置 - 验证码邮箱"></a>
<a id="tocS全局配置 - 验证码邮箱"></a>
<a id="tocs全局配置 - 验证码邮箱"></a>

```json
{
  "type": "object",
  "properties": {
    "password": {
      "type": "string",
      "title": "密码"
    },
    "account": {
      "type": "string",
      "title": "账号"
    },
    "body": {
      "type": "string",
      "title": "邮件内容",
      "description": "一串html代码，其中放验证码的地方为：[[${verifyCode}]]"
    },
    "title": {
      "type": "string",
      "title": "邮件主题"
    },
    "host": {
      "type": "string",
      "title": "邮箱HOST",
      "description": "邮箱HOST，如： smtp.exmail.qq.com"
    },
    "port": {
      "type": "string",
      "title": "邮箱PORT",
      "description": "邮箱HOST的PORT，例如465"
    }
  },
  "required": [
    "password",
    "account",
    "body",
    "title",
    "host",
    "port"
  ],
  "title": "验证码邮箱"
}

```

验证码邮箱

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|password|string|true|none|none|
|account|string|true|none|none|
|body|string|true|none|一串html代码，其中放验证码的地方为：[[${verifyCode}]]|
|title|string|true|none|none|
|host|string|true|none|邮箱HOST，如： smtp.exmail.qq.com|
|port|string|true|none|邮箱HOST的PORT，例如465|

<h2 id="tocS_全局配置 - 页脚配置">全局配置 - 页脚配置</h2>
<!-- backwards compatibility -->
<a id="schema全局配置 - 页脚配置"></a>
<a id="schema_全局配置 - 页脚配置"></a>
<a id="tocS全局配置 - 页脚配置"></a>
<a id="tocs全局配置 - 页脚配置"></a>

```json
{
  "type": "object",
  "properties": {
    "logo": {
      "type": "string",
      "title": "页眉脚ogo uri"
    }
  },
  "title": "页眉信息",
  "required": [
    "logo"
  ]
}

```

页眉信息

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|logo|string|true|none|none|

<h2 id="tocS_全局配置 - 页眉配置">全局配置 - 页眉配置</h2>
<!-- backwards compatibility -->
<a id="schema全局配置 - 页眉配置"></a>
<a id="schema_全局配置 - 页眉配置"></a>
<a id="tocS全局配置 - 页眉配置"></a>
<a id="tocs全局配置 - 页眉配置"></a>

```json
{
  "type": "object",
  "properties": {
    "logo": {
      "type": "string",
      "title": "页眉logo uri"
    }
  },
  "title": "页眉信息",
  "required": [
    "logo"
  ]
}

```

页眉信息

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|logo|string|true|none|none|

<h2 id="tocS_评论信息（批量返回）">评论信息（批量返回）</h2>
<!-- backwards compatibility -->
<a id="schema评论信息（批量返回）"></a>
<a id="schema_评论信息（批量返回）"></a>
<a id="tocS评论信息（批量返回）"></a>
<a id="tocs评论信息（批量返回）"></a>

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "title": "评论id",
      "description": "评论id"
    },
    "author": {
      "type": "object",
      "properties": {
        "email": {
          "type": "string",
          "title": "邮箱"
        },
        "username": {
          "type": "string",
          "title": "用户名"
        },
        "roles": {
          "type": "array",
          "items": {
            "type": "string",
            "enum": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "ROLE_ADMIN": "管理员",
                "ROLE_USER": "普通用户"
              }
            }
          },
          "title": "权限列表",
          "uniqueItems": true
        },
        "avatar": {
          "type": "string",
          "title": "头像"
        }
      },
      "required": [
        "email",
        "username",
        "roles",
        "avatar"
      ]
    },
    "article": {
      "type": "object",
      "properties": {
        "id": {
          "type": "integer",
          "title": "文章id"
        },
        "author": {
          "type": "object",
          "properties": {
            "email": {
              "type": "string",
              "title": "邮箱"
            },
            "username": {
              "type": "string",
              "title": "用户名"
            },
            "roles": {
              "type": "array",
              "items": {
                "type": "string",
                "enum": [
                  "ROLE_ADMIN",
                  "ROLE_USER"
                ],
                "x-apifox": {
                  "enumDescriptions": {
                    "ROLE_ADMIN": "管理员",
                    "ROLE_USER": "普通用户"
                  }
                }
              },
              "title": "权限列表",
              "uniqueItems": true
            },
            "avatar": {
              "type": "string",
              "title": "头像"
            }
          },
          "required": [
            "email",
            "username",
            "roles",
            "avatar"
          ]
        },
        "status": {
          "type": "string",
          "title": "状态",
          "enum": [
            "NORMAL",
            "BLOCKED"
          ],
          "x-apifox": {
            "enumDescriptions": {
              "NORMAL": "正常",
              "BLOCKED": "屏蔽"
            }
          },
          "default": "NORMAL",
          "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
        },
        "publishDate": {
          "type": "string",
          "title": "创建时间"
        },
        "updateDate": {
          "type": "string",
          "title": "更新时间"
        },
        "views": {
          "type": "integer",
          "title": "被浏览次数"
        },
        "likes": {
          "type": "integer",
          "title": "点赞量"
        },
        "favorites": {
          "type": "integer",
          "title": "收藏量"
        },
        "title": {
          "type": "string",
          "title": "标题"
        },
        "description": {
          "type": "string",
          "title": "简介"
        },
        "cover": {
          "type": "string",
          "title": "图片url地址"
        }
      },
      "required": [
        "id",
        "author",
        "status",
        "publishDate",
        "updateDate",
        "views",
        "title",
        "description",
        "cover",
        "likes",
        "favorites"
      ]
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"Normal\": \"正常\",             \"Blocked\": \"屏蔽\""
    },
    "content": {
      "type": "string",
      "title": "评论内容"
    },
    "publishDateTime": {
      "type": "string",
      "title": "发表时间",
      "description": "'yyyy-MM-dd HH:mm:ss'"
    },
    "likes": {
      "type": "integer",
      "title": "点赞数量",
      "description": "默认为0"
    },
    "parent": {
      "type": "integer",
      "title": "父级评论",
      "description": "父级评论的id，无父评论的为0"
    }
  },
  "required": [
    "id",
    "article",
    "author",
    "status",
    "content",
    "publishDateTime",
    "parent",
    "likes"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|id|integer|true|none|评论id|
|author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|article|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|status|[%E8%AF%84%E8%AE%BA%E7%8A%B6%E6%80%81%20Status](#schema%e8%af%84%e8%ae%ba%e7%8a%b6%e6%80%81%20status)|true|none|"Normal": "正常",             "Blocked": "屏蔽"|
|content|string|true|none|none|
|publishDateTime|string|true|none|'yyyy-MM-dd HH:mm:ss'|
|likes|integer|true|none|默认为0|
|parent|integer|true|none|父级评论的id，无父评论的为0|

<h2 id="tocS_评论信息（详细）">评论信息（详细）</h2>
<!-- backwards compatibility -->
<a id="schema评论信息（详细）"></a>
<a id="schema_评论信息（详细）"></a>
<a id="tocS评论信息（详细）"></a>
<a id="tocs评论信息（详细）"></a>

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "title": "评论id",
      "description": "评论id"
    },
    "author": {
      "type": "object",
      "properties": {
        "email": {
          "type": "string",
          "title": "邮箱"
        },
        "username": {
          "type": "string",
          "title": "用户名"
        },
        "roles": {
          "type": "array",
          "items": {
            "type": "string",
            "enum": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "ROLE_ADMIN": "管理员",
                "ROLE_USER": "普通用户"
              }
            }
          },
          "title": "权限列表",
          "uniqueItems": true
        },
        "avatar": {
          "type": "string",
          "title": "头像"
        }
      },
      "required": [
        "email",
        "username",
        "roles",
        "avatar"
      ]
    },
    "article": {
      "type": "object",
      "properties": {
        "id": {
          "type": "integer",
          "title": "文章id"
        },
        "author": {
          "type": "object",
          "properties": {
            "email": {
              "type": "string",
              "title": "邮箱"
            },
            "username": {
              "type": "string",
              "title": "用户名"
            },
            "roles": {
              "type": "array",
              "items": {
                "type": "string",
                "enum": [
                  "ROLE_ADMIN",
                  "ROLE_USER"
                ],
                "x-apifox": {
                  "enumDescriptions": {
                    "ROLE_ADMIN": "管理员",
                    "ROLE_USER": "普通用户"
                  }
                }
              },
              "title": "权限列表",
              "uniqueItems": true
            },
            "avatar": {
              "type": "string",
              "title": "头像"
            }
          },
          "required": [
            "email",
            "username",
            "roles",
            "avatar"
          ]
        },
        "status": {
          "type": "string",
          "title": "状态",
          "enum": [
            "NORMAL",
            "BLOCKED"
          ],
          "x-apifox": {
            "enumDescriptions": {
              "NORMAL": "正常",
              "BLOCKED": "屏蔽"
            }
          },
          "default": "NORMAL",
          "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
        },
        "publishDate": {
          "type": "string",
          "title": "创建时间"
        },
        "updateDate": {
          "type": "string",
          "title": "更新时间"
        },
        "views": {
          "type": "integer",
          "title": "被浏览次数"
        },
        "likes": {
          "type": "integer",
          "title": "点赞量"
        },
        "favorites": {
          "type": "integer",
          "title": "收藏量"
        },
        "title": {
          "type": "string",
          "title": "标题"
        },
        "description": {
          "type": "string",
          "title": "简介"
        },
        "cover": {
          "type": "string",
          "title": "图片url地址"
        }
      },
      "required": [
        "id",
        "author",
        "status",
        "publishDate",
        "updateDate",
        "views",
        "title",
        "description",
        "cover",
        "likes",
        "favorites"
      ]
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"Normal\": \"正常\",             \"Blocked\": \"屏蔽\""
    },
    "content": {
      "type": "string",
      "title": "评论内容"
    },
    "publishDateTime": {
      "type": "string",
      "title": "发表时间",
      "description": "'yyyy-MM-dd HH:mm:ss'"
    },
    "likes": {
      "type": "integer",
      "title": "点赞数量",
      "description": "默认为0"
    },
    "parent": {
      "type": "integer",
      "title": "父级评论",
      "description": "父级评论的id，无父评论的为0"
    }
  },
  "required": [
    "id",
    "article",
    "author",
    "status",
    "content",
    "publishDateTime",
    "parent",
    "likes"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|id|integer|true|none|评论id|
|author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|article|[%E6%96%87%E7%AB%A0%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e6%96%87%e7%ab%a0%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|status|[%E8%AF%84%E8%AE%BA%E7%8A%B6%E6%80%81%20Status](#schema%e8%af%84%e8%ae%ba%e7%8a%b6%e6%80%81%20status)|true|none|"Normal": "正常",             "Blocked": "屏蔽"|
|content|string|true|none|none|
|publishDateTime|string|true|none|'yyyy-MM-dd HH:mm:ss'|
|likes|integer|true|none|默认为0|
|parent|integer|true|none|父级评论的id，无父评论的为0|

<h2 id="tocS_前端配置">前端配置</h2>
<!-- backwards compatibility -->
<a id="schema前端配置"></a>
<a id="schema_前端配置"></a>
<a id="tocS前端配置"></a>
<a id="tocs前端配置"></a>

```json
{
  "type": "object",
  "properties": {
    "favicon": {
      "type": "string",
      "description": "网站图标 url",
      "title": "站点图标"
    },
    "title": {
      "type": "string",
      "description": "网站名",
      "title": "站点名称"
    },
    "logo": {
      "type": "string",
      "title": "主logo"
    },
    "headerDto": {
      "type": "object",
      "properties": {
        "logo": {
          "type": "string",
          "title": "页眉logo uri"
        }
      },
      "title": "页眉信息",
      "required": [
        "logo"
      ]
    },
    "footerDto": {
      "type": "object",
      "properties": {
        "logo": {
          "type": "string",
          "title": "页眉脚ogo uri"
        }
      },
      "title": "页眉信息",
      "required": [
        "logo"
      ]
    }
  },
  "required": [
    "favicon",
    "title",
    "logo",
    "headerDto",
    "footerDto"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|favicon|string|true|none|网站图标 url|
|title|string|true|none|网站名|
|logo|string|true|none|none|
|headerDto|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%20-%20%E9%A1%B5%E7%9C%89%E9%85%8D%E7%BD%AE](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%20-%20%e9%a1%b5%e7%9c%89%e9%85%8d%e7%bd%ae)|true|none|none|
|footerDto|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%20-%20%E9%A1%B5%E8%84%9A%E9%85%8D%E7%BD%AE](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%20-%20%e9%a1%b5%e8%84%9a%e9%85%8d%e7%bd%ae)|true|none|none|

<h2 id="tocS_评论状态 Status">评论状态 Status</h2>
<!-- backwards compatibility -->
<a id="schema评论状态 status"></a>
<a id="schema_评论状态 Status"></a>
<a id="tocS评论状态 status"></a>
<a id="tocs评论状态 status"></a>

```json
{
  "type": "string",
  "title": "状态",
  "enum": [
    "NORMAL",
    "BLOCKED"
  ],
  "x-apifox": {
    "enumDescriptions": {
      "NORMAL": "正常",
      "BLOCKED": "屏蔽"
    }
  },
  "default": "NORMAL",
  "description": "\"Normal\": \"正常\",             \"Blocked\": \"屏蔽\""
}

```

状态

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|状态|string|false|none|"Normal": "正常",             "Blocked": "屏蔽"|

#### 枚举值

|属性|值|
|---|---|
|状态|NORMAL|
|状态|BLOCKED|

<h2 id="tocS_全局配置（详细）">全局配置（详细）</h2>
<!-- backwards compatibility -->
<a id="schema全局配置（详细）"></a>
<a id="schema_全局配置（详细）"></a>
<a id="tocS全局配置（详细）"></a>
<a id="tocs全局配置（详细）"></a>

```json
{
  "type": "object",
  "properties": {
    "favicon": {
      "type": "string",
      "description": "网站图标 url",
      "title": "站点图标"
    },
    "title": {
      "type": "string",
      "description": "网站名",
      "title": "站点名称"
    },
    "logo": {
      "type": "string",
      "title": "主logo"
    },
    "emailDto": {
      "type": "object",
      "properties": {
        "password": {
          "type": "string",
          "title": "密码"
        },
        "account": {
          "type": "string",
          "title": "账号"
        },
        "body": {
          "type": "string",
          "title": "邮件内容",
          "description": "一串html代码，其中放验证码的地方为：[[${verifyCode}]]"
        },
        "title": {
          "type": "string",
          "title": "邮件主题"
        },
        "host": {
          "type": "string",
          "title": "邮箱HOST",
          "description": "邮箱HOST，如： smtp.exmail.qq.com"
        },
        "port": {
          "type": "string",
          "title": "邮箱PORT",
          "description": "邮箱HOST的PORT，例如465"
        }
      },
      "required": [
        "password",
        "account",
        "body",
        "title",
        "host",
        "port"
      ],
      "title": "验证码邮箱"
    },
    "headerDto": {
      "type": "object",
      "properties": {
        "logo": {
          "type": "string",
          "title": "页眉logo uri"
        }
      },
      "title": "页眉信息",
      "required": [
        "logo"
      ]
    },
    "footerDto": {
      "type": "object",
      "properties": {
        "logo": {
          "type": "string",
          "title": "页眉脚ogo uri"
        }
      },
      "title": "页眉信息",
      "required": [
        "logo"
      ]
    }
  },
  "required": [
    "favicon",
    "title",
    "logo",
    "emailDto",
    "headerDto",
    "footerDto"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|favicon|string|true|none|网站图标 url|
|title|string|true|none|网站名|
|logo|string|true|none|none|
|emailDto|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%20-%20%E9%AA%8C%E8%AF%81%E7%A0%81%E9%82%AE%E7%AE%B1](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%20-%20%e9%aa%8c%e8%af%81%e7%a0%81%e9%82%ae%e7%ae%b1)|true|none|none|
|headerDto|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%20-%20%E9%A1%B5%E7%9C%89%E9%85%8D%E7%BD%AE](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%20-%20%e9%a1%b5%e7%9c%89%e9%85%8d%e7%bd%ae)|true|none|none|
|footerDto|[%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE%20-%20%E9%A1%B5%E8%84%9A%E9%85%8D%E7%BD%AE](#schema%e5%85%a8%e5%b1%80%e9%85%8d%e7%bd%ae%20-%20%e9%a1%b5%e8%84%9a%e9%85%8d%e7%bd%ae)|true|none|none|

<h2 id="tocS_文章信息（批量返回）">文章信息（批量返回）</h2>
<!-- backwards compatibility -->
<a id="schema文章信息（批量返回）"></a>
<a id="schema_文章信息（批量返回）"></a>
<a id="tocS文章信息（批量返回）"></a>
<a id="tocs文章信息（批量返回）"></a>

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "title": "文章id"
    },
    "author": {
      "type": "object",
      "properties": {
        "email": {
          "type": "string",
          "title": "邮箱"
        },
        "username": {
          "type": "string",
          "title": "用户名"
        },
        "roles": {
          "type": "array",
          "items": {
            "type": "string",
            "enum": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "ROLE_ADMIN": "管理员",
                "ROLE_USER": "普通用户"
              }
            }
          },
          "title": "权限列表",
          "uniqueItems": true
        },
        "avatar": {
          "type": "string",
          "title": "头像"
        }
      },
      "required": [
        "email",
        "username",
        "roles",
        "avatar"
      ]
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
    },
    "publishDate": {
      "type": "string",
      "title": "创建时间"
    },
    "updateDate": {
      "type": "string",
      "title": "更新时间"
    },
    "views": {
      "type": "integer",
      "title": "被浏览次数"
    },
    "likes": {
      "type": "integer",
      "title": "点赞量"
    },
    "favorites": {
      "type": "integer",
      "title": "收藏量"
    },
    "title": {
      "type": "string",
      "title": "标题"
    },
    "description": {
      "type": "string",
      "title": "简介"
    },
    "cover": {
      "type": "string",
      "title": "图片url地址"
    }
  },
  "required": [
    "id",
    "author",
    "status",
    "publishDate",
    "updateDate",
    "views",
    "title",
    "description",
    "cover",
    "likes",
    "favorites"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|id|integer|true|none|none|
|author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|publishDate|string|true|none|none|
|updateDate|string|true|none|none|
|views|integer|true|none|none|
|likes|integer|true|none|none|
|favorites|integer|true|none|none|
|title|string|true|none|none|
|description|string|true|none|none|
|cover|string|true|none|none|

<h2 id="tocS_文章信息（创建返回包）">文章信息（创建返回包）</h2>
<!-- backwards compatibility -->
<a id="schema文章信息（创建返回包）"></a>
<a id="schema_文章信息（创建返回包）"></a>
<a id="tocS文章信息（创建返回包）"></a>
<a id="tocs文章信息（创建返回包）"></a>

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "title": "文章id"
    },
    "author": {
      "type": "object",
      "properties": {
        "email": {
          "type": "string",
          "title": "邮箱"
        },
        "username": {
          "type": "string",
          "title": "用户名"
        },
        "roles": {
          "type": "array",
          "items": {
            "type": "string",
            "enum": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "ROLE_ADMIN": "管理员",
                "ROLE_USER": "普通用户"
              }
            }
          },
          "title": "权限列表",
          "uniqueItems": true
        },
        "avatar": {
          "type": "string",
          "title": "头像"
        }
      },
      "required": [
        "email",
        "username",
        "roles",
        "avatar"
      ]
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
    },
    "publishDate": {
      "type": "string",
      "title": "创建时间"
    },
    "updateDate": {
      "type": "string",
      "title": "更新时间"
    },
    "views": {
      "type": "integer",
      "title": "被浏览次数"
    },
    "likes": {
      "type": "integer",
      "title": "点赞量"
    },
    "favorites": {
      "type": "integer",
      "title": "收藏量"
    },
    "title": {
      "type": "string",
      "title": "标题"
    },
    "description": {
      "type": "string",
      "title": "简介"
    },
    "cover": {
      "type": "string",
      "title": "图片url地址"
    },
    "content": {
      "type": "string",
      "title": "正文内容"
    }
  },
  "required": [
    "id",
    "author",
    "status",
    "publishDate",
    "updateDate",
    "views",
    "title",
    "description",
    "cover",
    "likes",
    "favorites",
    "content"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|id|integer|true|none|none|
|author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|publishDate|string|true|none|none|
|updateDate|string|true|none|none|
|views|integer|true|none|none|
|likes|integer|true|none|none|
|favorites|integer|true|none|none|
|title|string|true|none|none|
|description|string|true|none|none|
|cover|string|true|none|none|
|content|string|true|none|none|

<h2 id="tocS_文章信息（详细）">文章信息（详细）</h2>
<!-- backwards compatibility -->
<a id="schema文章信息（详细）"></a>
<a id="schema_文章信息（详细）"></a>
<a id="tocS文章信息（详细）"></a>
<a id="tocs文章信息（详细）"></a>

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "title": "文章id"
    },
    "author": {
      "type": "object",
      "properties": {
        "email": {
          "type": "string",
          "title": "邮箱"
        },
        "username": {
          "type": "string",
          "title": "用户名"
        },
        "roles": {
          "type": "array",
          "items": {
            "type": "string",
            "enum": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ],
            "x-apifox": {
              "enumDescriptions": {
                "ROLE_ADMIN": "管理员",
                "ROLE_USER": "普通用户"
              }
            }
          },
          "title": "权限列表",
          "uniqueItems": true
        },
        "avatar": {
          "type": "string",
          "title": "头像"
        }
      },
      "required": [
        "email",
        "username",
        "roles",
        "avatar"
      ]
    },
    "status": {
      "type": "string",
      "title": "状态",
      "enum": [
        "NORMAL",
        "BLOCKED"
      ],
      "x-apifox": {
        "enumDescriptions": {
          "NORMAL": "正常",
          "BLOCKED": "屏蔽"
        }
      },
      "default": "NORMAL",
      "description": "\"NORMAL\": \"正常\",             \"BLOCKED\": \"屏蔽\""
    },
    "publishDate": {
      "type": "string",
      "title": "创建时间"
    },
    "updateDate": {
      "type": "string",
      "title": "更新时间"
    },
    "views": {
      "type": "integer",
      "title": "被浏览次数"
    },
    "likes": {
      "type": "integer",
      "title": "点赞量"
    },
    "favorites": {
      "type": "integer",
      "title": "收藏量"
    },
    "title": {
      "type": "string",
      "title": "标题"
    },
    "description": {
      "type": "string",
      "title": "简介"
    },
    "cover": {
      "type": "string",
      "title": "图片url地址"
    },
    "content": {
      "type": "string",
      "title": "正文内容"
    },
    "liked": {
      "type": "boolean",
      "title": "是否点赞过",
      "description": "已登录用户返回该用户是否登录过未登录用户返回false"
    },
    "favorited": {
      "type": "boolean",
      "title": "是否收藏过"
    },
    "authored": {
      "type": "boolean",
      "title": "是否作者本人"
    }
  },
  "required": [
    "id",
    "author",
    "status",
    "publishDate",
    "updateDate",
    "views",
    "title",
    "description",
    "cover",
    "likes",
    "favorites",
    "content",
    "liked",
    "favorited",
    "authored"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|id|integer|true|none|none|
|author|[%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%EF%BC%88%E6%89%B9%E9%87%8F%E8%BF%94%E5%9B%9E%EF%BC%89](#schema%e7%94%a8%e6%88%b7%e4%bf%a1%e6%81%af%ef%bc%88%e6%89%b9%e9%87%8f%e8%bf%94%e5%9b%9e%ef%bc%89)|true|none|none|
|status|[%E6%96%87%E7%AB%A0%E7%8A%B6%E6%80%81%20Status](#schema%e6%96%87%e7%ab%a0%e7%8a%b6%e6%80%81%20status)|true|none|"NORMAL": "正常",             "BLOCKED": "屏蔽"|
|publishDate|string|true|none|none|
|updateDate|string|true|none|none|
|views|integer|true|none|none|
|likes|integer|true|none|none|
|favorites|integer|true|none|none|
|title|string|true|none|none|
|description|string|true|none|none|
|cover|string|true|none|none|
|content|string|true|none|none|
|liked|boolean|true|none|已登录用户返回该用户是否登录过未登录用户返回false|
|favorited|boolean|true|none|none|
|authored|boolean|true|none|none|

<h2 id="tocS_评论信息（创建)">评论信息（创建)</h2>
<!-- backwards compatibility -->
<a id="schema评论信息（创建)"></a>
<a id="schema_评论信息（创建)"></a>
<a id="tocS评论信息（创建)"></a>
<a id="tocs评论信息（创建)"></a>

```json
{
  "type": "object",
  "properties": {
    "content": {
      "type": "string",
      "title": "评论内容"
    },
    "parent": {
      "type": "integer",
      "description": "无父评论的为0",
      "title": "父级评论"
    }
  },
  "required": [
    "content",
    "parent"
  ]
}

```

### 属性

|名称|类型|必选|约束|说明|
|---|---|---|---|---|
|content|string|true|none|none|
|parent|integer|true|none|无父评论的为0|

