package cn.geekyspace.blog.controller

import cn.geekyspace.blog.repository.ArticleRepository
import cn.geekyspace.blog.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * REST 控制器：提供文章相关的 API 接口
 */
@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository) {

    /**
     * 获取所有文章
     * @return 按添加时间倒序排列的文章列表
     */
    @GetMapping("/")
    fun findAll() = repository.findAllByOrderByAddedAtDesc()

    /**
     * 根据 slug 获取单篇文章
     * @param slug 文章的 URL-friendly 标识符
     * @return 查询到的文章对象
     * @throws ResponseStatusException 文章不存在时返回 404
     */
    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) =
        repository.findBySlug(slug) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This article does not exist"
        )

}

/**
 * REST 控制器：提供用户相关的 API 接口
 */
@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @GetMapping("/")
    fun findAll() = repository.findAll()

    /**
     * 根据 login 获取单个用户
     * @param login 用户登录名
     * @return 查询到的用户对象
     * @throws ResponseStatusException 用户不存在时返回 404
     */
    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
        repository.findByLogin(login) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )
}