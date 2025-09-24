# Spring Boot Kotlin 博客应用

一个使用 Spring Boot 和 Kotlin 构建的现代化博客 Web 应用程序，支持文章管理和用户系统。

项目构建参考：https://spring.io/guides/tutorials/spring-boot-kotlin

## 🚀 技术栈

### 后端框架
- **Spring Boot 3.5.6** - 企业级 Java/Kotlin 应用框架
- **Spring Data JPA** - 数据持久化层
- **Spring Web** - Web 应用开发
- **Kotlin 1.9.25** - 现代 JVM 编程语言

### 模板引擎
- **Mustache** - 轻量级模板引擎，支持服务端渲染

### 数据库
- **H2 Database** - 内存数据库（开发环境）

### 测试框架
- **JUnit 5** - 单元测试框架
- **Kotest** - Kotlin 原生测试框架
- **SpringMockK** - Kotlin Mock 支持

### 开发工具
- **Spring Boot DevTools** - 热重载和开发工具
- **Gradle** - 构建工具

## 📁 项目结构

```
src/main/kotlin/cn/geekyspace/blog/
├── BlogApplication.kt              # 应用启动类
├── config/
│   └── BlogConfiguration.kt       # 应用配置
├── controller/
│   ├── HtmlController.kt          # Web 页面控制器
│   └── HttpControllers.kt         # REST API 控制器
├── entity/
│   └── Entities.kt                # JPA 实体类（Article, User）
├── extend/
│   └── Extensions.kt              # Kotlin 扩展函数
└── repository/
    └── Repositories.kt            # 数据访问层

src/main/resources/
├── application.yaml               # 应用配置文件
├── static/                        # 静态资源目录
└── templates/                     # Mustache 模板文件
    ├── _header.mustache          # 页面头部模板
    ├── _footer.mustache          # 页面底部模板
    └── blog.mustache             # 博客首页模板
```

## 🛠️ 功能特性

### Web 页面
- **博客首页** (`/`) - 显示所有文章列表
- **文章详情页** (`/article/{slug}`) - 显示单篇文章内容

### REST API
- **文章 API** (`/api/article/`)
  - `GET /api/article/` - 获取所有文章
  - `GET /api/article/{slug}` - 获取指定文章
- **用户 API** (`/api/user/`)
  - `GET /api/user/` - 获取所有用户
  - `GET /api/user/{login}` - 获取指定用户

### 数据模型
- **Article（文章）** - 包含标题、摘要、内容、作者、发布时间等
- **User（用户）** - 包含登录名、姓名、描述等信息

## 🚀 快速开始

### 环境要求
- Java 17+
- Gradle 7.0+

### 运行应用

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd blog
   ```

2. **运行应用**
   ```bash
   ./gradlew bootRun
   ```

3. **访问应用**
   - 博客首页：http://localhost:8080/
   - 文章 API：http://localhost:8080/api/article/
   - 用户 API：http://localhost:8080/api/user/

### 构建应用

```bash
# 编译项目
./gradlew build

# 运行测试
./gradlew test

# 打包应用
./gradlew bootJar
```

## 🔧 配置说明

### 应用配置 (`application.yaml`)
```yaml
spring:
  application:
    name: Blog
  jpa:
    properties:
      hibernate:
        globally_quoted_identifiers: true
        globally_quoted_identifiers_skip_column_definitions: true
```

### Kotlin 编译器配置
- `-Xjsr305=strict` - 严格处理 Java 的 @Nullable/@NotNull 注解
- `-Xjvm-default=all` - 接口默认实现生成 Java default method

## 🧪 测试

项目使用 Kotest 作为主要测试框架，支持：
- 单元测试
- 集成测试
- 属性测试
- 数据驱动测试

运行测试：
```bash
./gradlew test
```

## 📝 开发说明

### 包结构设计
- `cn.geekyspace.blog` - 主包
- `controller` - 控制器层
- `entity` - 实体类
- `repository` - 数据访问层
- `config` - 配置类
- `extend` - 扩展函数

### 关键特性
- **组件扫描** - Spring Boot 自动扫描 `cn.geekyspace.blog` 包及其子包
- **JPA 实体** - 使用 `allOpen` 插件自动处理 Kotlin 类的 final 修饰符
- **模板渲染** - Mustache 模板引擎支持服务端渲染
- **RESTful API** - 提供完整的 REST API 接口

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目基于 MIT 许可证开源 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🔗 相关链接

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Kotlin 官方文档](https://kotlinlang.org/docs/)
- [Mustache 模板语法](https://mustache.github.io/mustache.5.html)
- [项目构建参考教程](https://spring.io/guides/tutorials/spring-boot-kotlin)

