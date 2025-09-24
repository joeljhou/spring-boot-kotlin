package cn.geekyspace.blog

import cn.geekyspace.blog.entity.Article
import cn.geekyspace.blog.entity.User
import cn.geekyspace.blog.repository.ArticleRepository
import cn.geekyspace.blog.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

/**
 * 针对 UserRepository 和 ArticleRepository 的 JPA 仓库测试类
 * 使用 @DataJpaTest 自动配置内存数据库和 JPA 环境
 */
@DataJpaTest
class RepositoriesTests @Autowired constructor(
  val entityManager: TestEntityManager,    // 测试用的实体管理器，方便持久化和刷新
  val userRepository: UserRepository,      // 注入 UserRepository
  val articleRepository: ArticleRepository // 注入 ArticleRepository
) {

  /**
   * 测试 ArticleRepository.findByIdOrNull 方法
   * 1. 创建一个 User 实例并持久化
   * 2. 创建一个 Article 实例并关联该用户
   * 3. 持久化 Article 并刷新 EntityManager
   * 4. 调用 findByIdOrNull 查询文章并断言结果正确
   */
  @Test
  fun `When findByIdOrNull then return Article`() {
    val johnDoe = User("johnDoe", "John", "Doe")
    entityManager.persist(johnDoe)

    val article = Article("Lorem", "Lorem", "dolor sit amet", johnDoe)
    entityManager.persist(article)
    entityManager.flush() // 确保实体已写入数据库

    val found = articleRepository.findByIdOrNull(article.id!!)
    assertThat(found).isEqualTo(article) // 断言查询到的文章与持久化的文章相同
  }

  /**
   * 测试 UserRepository.findByLogin 方法
   * 1. 创建一个 User 并持久化
   * 2. 调用 findByLogin 查询用户并断言结果正确
   */
  @Test
  fun `When findByLogin then return User`() {
    val johnDoe = User("johnDoe", "John", "Doe")
    entityManager.persist(johnDoe)
    entityManager.flush() // 确保实体已写入数据库

    val user = userRepository.findByLogin(johnDoe.login)
    assertThat(user).isEqualTo(johnDoe) // 断言查询到的用户与持久化的用户相同
  }
}
