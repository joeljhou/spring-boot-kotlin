package cn.geekyspace.blog.controller

import cn.geekyspace.blog.entity.Article
import cn.geekyspace.blog.entity.User
import cn.geekyspace.blog.extend.format
import cn.geekyspace.blog.repository.ArticleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

/**
 * Web 控制器类
 * 负责处理博客首页和文章详情页请求
 */
@Controller
class HtmlController(private val repository: ArticleRepository) {


    /**
     * 处理根路径 "/" 请求
     * - 查询所有文章，按添加时间倒序排列
     * - 将文章列表和页面标题传递给视图模板
     * @param model 模型对象，用于向视图传递数据
     * @return 返回 "blog" 模板名称，由模板引擎渲染
     */
    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "blog"
    }

    /**
     * 处理文章详情页请求 "/article/{slug}"
     * @param slug 文章的 URL-friendly 标识符
     * @param model 模型对象，用于向视图传递数据
     * @return 返回文章详情页模板 "article"
     * @throws ResponseStatusException 如果文章不存在则返回 404
     */
    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = repository
            .findBySlug(slug)
            ?.render()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

    /**
     * 扩展函数：将 Article 实体转换为渲染用对象 RenderedArticle
     * - 格式化日期 addedAt
     */
    fun Article.render() = RenderedArticle(
        slug,
        title,
        headline,
        content,
        author,
        addedAt.format()
    )

    /**
     * 渲染用文章数据类
     * - 用于传递给视图模板，避免直接暴露实体类
     */
    data class RenderedArticle(
        val slug: String,
        val title: String,
        val headline: String,
        val content: String,
        val author: User,
        val addedAt: String)

}