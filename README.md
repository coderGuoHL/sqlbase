# SQL Father - 模拟数据生成器（后端）
代码项目地址（魔改版本）：

后端代码仓库：https://github.com/coderGuoHL/sqlbase

前端代码仓库: 




源项目后端代码地址（鱼皮项目）：
前端代码仓库：[https://github.com/liyupi/sql-father-frontend-public](https://github.com/liyupi/sql-father-frontend-public)
后端代码仓库：[https://github.com/liyupi/sql-father-backend-public](https://github.com/liyupi/sql-father-backend-public)

## 项目背景

基于鱼皮的sql-father项目，以及写dao层代码的重复性工作。所以衍生出了用sqlfather作为基础，然后在其已有功能点上做出提升的想法。





## 功能大全 + 应用场景 +技术介绍

请参考源项目后端readme文档。





## 详细设计

### Schema 构造器

核心类：TableSchemaBuilder，作用是将不同的参数统一收敛为 TableSchema 对象。

其中，buildFromSql（根据 SQL 生成 Schema）使用了 Druid 数据库连接池自带的语法解析器，非常强大。（解析器这种东西一般不要自己写，有这时间你都能做几个项目了，写出来还没人家的好用)



### Schema 定义

用于保存表和字段的信息，结构如下：

```json
{
  "dbName": "库名",
  "tableName": "test_table",
  "tableComment": "表注释",
  "mockNum": 20,
  "fieldList": [{
    "fieldName": "username",
    "comment": "用户名",
    "fieldType": "varchar(256)",
    "mockType": "随机",
    "mockParams": "人名",
    "notNull": true,
    "primaryKey": false,
    "autoIncrement": false
  }]
}
```



### 生成器

#### 多种生成类型

将每种生成类型定义为一个 Builder（core/builder 目录）：

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666145274014-bb582f01-31dd-442c-835a-64c1e9fd61a5-20221019132502741-20221019132512095.png)

其中，对于 SQL 代码生成器（ SqlBuilder），使用方言来支持不同的数据库类型（策略模式），并使用单例模式 + 工厂模式创建方言实例。

对于 Java、前端代码生成器（JavaCodeBuilder、FrontendCodeBuilder），使用 FreeMarker 模板引擎来生成。
模板代码如下：





#### 多种模拟数据生成规则

每种生成规则定义为一个 Generator，使用 DataGeneratorFactory（工厂模式）对多个 Generator 实例进行统一的创建和管理。



使用 dataFaker 库实现随机数据生成（RandomDataGenerator）。

使用 Generex 库实现正则表达式数据生成（RuleDataGenerator)。



#### 统一的生成入口

使用门面模式聚合各种生成类型，提供统一的生成调用和校验方法：





## 优化功能点

todo列表：

1. 生成一个基于entity代码生成dao层代码生成模版。
   - 修改前端新增一个生成模版展示dao层展示
   - 修改后端，新增一个模版生成dao层代码
2. 



## 致谢

部分词库来源：[https://github.com/fighting41love/funNLP](https://github.com/fighting41love/funNLP)

示例表信息来源：[https://open.yesapi.cn/list1.html](https://open.yesapi.cn/list1.html)

项目基础版本来源： https://github.com/liyupi

