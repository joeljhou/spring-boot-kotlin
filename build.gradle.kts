import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"

    // Kotlin 编译器插件，用于将 Kotlin 代码编译成 JVM 字节码。自动包含了Kotlin 标准库（kotlin-stdlib），不需要手动添加依赖
    kotlin("jvm") version "1.9.25"
    // 为了使 Kotlin 默认修饰符final 与 Spring 框架更好地协作，自动将被 Spring 注解（@Configuration 或 @Transactional）标注的类和方法设为 open
    kotlin("plugin.spring") version "1.9.25"
    // 为了能够使用 Kotlin 非空属性与 JPA 一起使用，自动为 @Entity、@MappedSuperclass 或 @Embeddable 注解的类生成无参构造函数
    kotlin("plugin.jpa") version "1.9.25"
}

group = "cn.geekyspace.blog"
version = "0.0.1-SNAPSHOT"
description = "使用 Spring Boot 和 Kotlin 构建 Web 应用程序"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    compilerOptions {
        // -Xjsr305=strict  严格处理 Java 的 @Nullable/@NotNull 注解，减少 NPE
        // -Xjvm-default=all 接口默认实现生成 Java default method，提升互操作性
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

springBoot {
    mainClass.set("cn.geekyspace.blog.BlogApplicationKt")
}

repositories {
    // 本地 Maven
    mavenLocal()
    // 本地 libs 目录（放置公司私有 jar）
    flatDir {
        dirs("libs")
    }
    // 阿里云 Maven 镜像
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    maven("https://maven.aliyun.com/repository/spring")
    maven("https://maven.aliyun.com/repository/spring-plugin")
    // 官方仓库
    google()
    mavenCentral()
    gradlePluginPortal()    // Gradle 插件仓库
    maven(uri("https://plugins.gradle.org/m2/")) // 备用插件仓库
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // 轻量级的模板引擎
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    // Spring Boot Web 应用常用的 Kotlin 依赖：
    // - kotlin-reflect: Kotlin 反射支持（运行时必需）
    // - jackson-module-kotlin: Jackson 对 Kotlin 类/数据类的序列化与反序列化支持
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // 只在开发环境生效，不会打包到生产 jar/war，用于热部署
    // - 监控 classpath 下的静态资源和模板文件（如 HTML、CSS、JS、Mustache/Thymeleaf 模板等）
    // - 通过 LiveReload 协议自动刷新浏览器
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    // Spring Boot 测试起步依赖（JUnit 5、AssertJ、Hamcrest、Mockito、Spring Test、JsonPath 等）
    // 内部已包含 junit-jupiter-engine（JUnit 5 测试执行引擎）
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // Kotlin 默认类是 final，Mockito 不支持直接 mock final 类
        // Kotlin 社区更推荐 MockK / Kotest，所以这里排除掉 Mockito
        exclude(module = "mockito-core")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")  // Kotlin JUnit 5 适配器
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")  // JUnit 运行器，让 IDE（IntelliJ IDEA）或 Gradle 正确发现运行 JUnit 5 测试
    implementation("com.ninja-squad:springmockk:4.0.2")            // Kotlin Mockito 适配器（可选），提供 @MockkBean 注解对象自动注入，替代 Spring 的 @MockBean
    // Kotest 测试框架
    testImplementation("io.kotest:kotest-assertions-core:6.0")     // 核心断言库（通常必用），快速开始：https://kotest.io/docs/assertions/assertions.html
    testImplementation("io.kotest:kotest-runner-junit5:6.0")       // 必须，Junit5 Runner
    testImplementation("io.kotest:kotest-property:6.0")            // 可选，属性测试
    testImplementation("io.kotest:kotest-framework-datatest:6.0")  // 可选，数据驱动测试
}

// Kotlin 类默认是 final，JPA/Hibernate 需要实体类可继承以支持懒加载。
// 使用 allOpen 插件，可以自动将标注了以下注解的类编译为 open，避免手动加 open
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
