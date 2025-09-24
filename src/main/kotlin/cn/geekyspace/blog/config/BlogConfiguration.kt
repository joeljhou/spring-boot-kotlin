package cn.geekyspace.blog.config

import cn.geekyspace.blog.entity.Article
import cn.geekyspace.blog.repository.ArticleRepository
import cn.geekyspace.blog.entity.User
import cn.geekyspace.blog.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring 配置类
 * 用于初始化博客应用的数据
 */
@Configuration
class BlogConfiguration {

    /**
     * 数据库初始化器 Bean
     * - 应用启动时自动执行
     * - 初始化示例用户和文章
     *
     * @param userRepository 注入 User 仓库，用于持久化用户
     * @param articleRepository 注入 Article 仓库，用于持久化文章
     * @return ApplicationRunner 在应用启动时执行初始化逻辑
     */
    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ) = ApplicationRunner {

        // 创建示例用户 John Doe 并保存
        val johnDoe = userRepository.save(User("johnDoe", "John", "Doe"))
        // 创建并保存示例文章
        // 创建并保存示例文章
        articleRepository.save(
            Article(
                title = "欢迎使用 Kotlin 博客",
                headline = "Kotlin 博客示例",
                content = """
            这是一个使用 Kotlin 和 Spring Boot 构建的博客示例。
            你可以在这里发布文章、管理用户，并学习 Kotlin Web 开发的基础知识。
            欢迎探索更多功能！
        """.trimIndent(),
                author = johnDoe
            )
        )

        articleRepository.save(
            Article(
                title = "Spring Boot 入门",
                headline = "快速搭建你的第一个应用",
                content = """
            Spring Boot 是一个用于简化 Spring 应用开发的框架。
            它提供了开箱即用的配置，让你专注于业务逻辑，而不必处理繁琐的配置。
            Kotlin 与 Spring Boot 配合，能更高效地开发现代 Web 应用。
        """.trimIndent(),
                author = johnDoe
            )
        )
    }
}