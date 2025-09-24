package cn.geekyspace.blog.repository

import cn.geekyspace.blog.entity.Article
import cn.geekyspace.blog.entity.User
import org.springframework.data.repository.CrudRepository

/**
 * Article 实体的 CRUD 仓库接口
 * 继承 CrudRepository 提供基本的增删改查操作
 */
interface ArticleRepository : CrudRepository<Article, Long> {

    /**
     * 根据文章 slug 查询文章
     * @param slug URL-friendly 的文章标识符
     * @return 找到的 Article 对象，找不到返回 null
     */
    fun findBySlug(slug: String): Article?

    /**
     * 查询所有文章，并按照添加时间倒序排列
     * @return Iterable<Article>，按 addedAt 倒序
     */
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

/**
 * User 实体的 CRUD 仓库接口
 * 继承 CrudRepository 提供基本的增删改查操作
 */
interface UserRepository : CrudRepository<User, Long> {

    /**
     * 根据登录名查询用户
     * @param login 用户登录名
     * @return 找到的 User 对象，找不到返回 null
     */
    fun findByLogin(login: String): User?
}